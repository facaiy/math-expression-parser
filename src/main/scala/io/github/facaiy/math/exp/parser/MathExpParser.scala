package io.github.facaiy.math.exp.parser

import io.github.facaiy.math.exp.{MathExpError, MathExpParserError}
import io.github.facaiy.math.exp.scanner._
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

/**
 * Created by facai on 6/8/17.
 */
object MathExpParser extends Parsers {
  override type Elem = MathExpToken

  class MathExpTokenReader(tokens: Seq[MathExpToken]) extends Reader[MathExpToken] {
    override def first: MathExpToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[MathExpToken] = new MathExpTokenReader(tokens.tail)
  }

  def constant: Parser[Constant] = accept("constant", {
    case x: INTEGER => Constant(x)
    case x: FLOAT => Constant(x)
  })

  /*
  def variable: Parser[Variable] = accept("value", {
    case x: VAR_NAME => Variable(x)
  })

  def functionName: Parser[FUNC_NAME] = accept("function name", {
    case f: FUNC_NAME => f
  })

  def function2: Parser[Operator2] =
    functionName ~ LEFT_PARENTHESIS ~ value ~ COMMA ~ value ~ RIGHT_PARENTHESIS ^^ {
      case FUNC_NAME(n) ~ _ ~ a1 ~ _ ~ a2 ~ _ => Operator2(n, a1, a2)
    }

  def binaryOpHigh: Parser[Operator2] = value ~ (MULTIPLY | DIVIDE) ~ value ^^ {
    case x ~ MULTIPLY ~ y => Operator2("*", x, y)
    case x ~ DIVIDE ~ y => Operator2("/", x, y)
  }
  */

  def shortFactor: Parser[MathExpAST] = constant | LEFT_PARENTHESIS ~> expression <~ RIGHT_PARENTHESIS ^^ { case x => x }

  def longFactor: Parser[MathExpAST] = shortFactor ~ rep(POWER ~ shortFactor) ^^ {
    case x ~ ls => ls.foldLeft[MathExpAST](x) {
      case (d1, POWER ~ d2) => Operator2(POWER.toString, d1, d2)
    }
  }

  def term: Parser[MathExpAST] = longFactor ~ rep((MULTIPLY | DIVIDE) ~ longFactor) ^^ {
    case x ~ ls => ls.foldLeft[MathExpAST](x) {
      case (d1, MULTIPLY ~ d2) => Operator2(MULTIPLY.toString, d1, d2)
      case (d1, DIVIDE ~ d2) => Operator2(DIVIDE.toString, d1, d2)
    }
  }

  def expression: Parser[MathExpAST] = term ~ rep1((ADD | MINUS) ~ term) ^^ {
    case x ~ ls => ls.foldLeft[MathExpAST](x){
      case (d1, ADD ~ d2) => Operator2(ADD.toString, d1, d2)
      case (d1, MINUS ~ d2) => Operator2(MINUS.toString, d1, d2)
    }
  }

  def program: Parser[MathExpAST] = phrase(expression)

  def apply(tokens: Seq[MathExpToken]): Either[MathExpParserError, MathExpAST] = {
    val reader = new MathExpTokenReader(tokens)

    program(reader) match {
      case NoSuccess(msg, next) => Left(MathExpParserError(msg))
      case Success(result, next) => Right(result)
    }
  }
}

object WorkflowCompiler {
  def apply(code: String): Either[MathExpError, MathExpAST] = {
    for {
      tokens <- MathExpScanner(code).right
      ast <- MathExpParser(tokens).right
    } yield ast
  }
}

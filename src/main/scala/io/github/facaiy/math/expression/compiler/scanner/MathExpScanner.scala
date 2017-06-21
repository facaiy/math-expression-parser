package io.github.facaiy.math.expression.compiler.scanner

import io.github.facaiy.math.expression._
import scala.util.parsing.combinator.JavaTokenParsers


/**
 * Created by facai on 6/6/17.
 *
 */
object MathExpScanner extends JavaTokenParsers {
  def add: Parser[Operator] = ADD.toString ^^ (_ => ADD)
  def minus: Parser[Operator] = MINUS.toString ^^ (_ => MINUS)
  def multiply: Parser[Operator] = MULTIPLY.toString ^^ (_ => MULTIPLY)
  def divide: Parser[Operator] = DIVIDE.toString ^^ (_ => DIVIDE)
  def power: Parser[Operator] = POWER.toString ^^ (_ => POWER)

  def comma: Parser[Delimiter] = COMMA.toString ^^ (_ => COMMA)
  def leftParenthesis: Parser[Delimiter] = LEFT_PARENTHESIS.toString ^^ (_ => LEFT_PARENTHESIS)
  def rightParenthesis: Parser[Delimiter] = RIGHT_PARENTHESIS.toString ^^ (_ => RIGHT_PARENTHESIS)

  def number: Parser[NUMBER] = floatingPointNumber ^^ (x => NUMBER(x.toDouble))

  def variable: Parser[VAR_NAME] = "$" ~ ident ^^ {
    case _ ~ n => VAR_NAME(n)
  }

  def function: Parser[FUNC_NAME] = ident ^^ (n => FUNC_NAME(n))

  def tokens: Parser[List[MathExpToken]] = {
    phrase(rep1(add | power | multiply | divide |
                comma | leftParenthesis | rightParenthesis |
                number |
                minus |                   // 负号与减号冲突
                variable | function))
  }

  def apply(expression: String): Either[MathExpScannerError, List[MathExpToken]] =
    parse(tokens, expression) match {
      case NoSuccess(msg, next) => Left(MathExpScannerError(msg))
      case Success(result, next) => Right(result)
    }
}

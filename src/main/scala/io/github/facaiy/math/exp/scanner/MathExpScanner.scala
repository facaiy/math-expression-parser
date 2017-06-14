package io.github.facaiy.math.exp.scanner

import io.github.facaiy.math.exp._
import scala.util.parsing.combinator.JavaTokenParsers


/**
 * Created by facai on 6/6/17.
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

  def integer: Parser[INTEGER] = """-?\d+""".r ^^ (x => INTEGER(x.toInt))

  def float: Parser[FLOAT] = """-?(\d+\.(\d*)?|\d*\.\d+)""".r ^^ (x => FLOAT(x.toDouble))

  def variable: Parser[VAR_NAME] = "$" ~ ident ^^ {
    case _ ~ n => VAR_NAME(n)
  }

  def function: Parser[FUNC_NAME] = ident ^^ (n => FUNC_NAME(n))

  def tokens: Parser[List[MathExpToken]] = {
    phrase(rep1(add | multiply | divide | power |
                comma | leftParenthesis | rightParenthesis |
                float | integer |
                minus |                   // 负号与减号冲突
                variable | function))
  }

  def apply(expression: String): Either[MathExpScannerError, List[MathExpToken]] =
    parse(tokens, expression) match {
      case NoSuccess(msg, next) => Left(MathExpScannerError(msg))
      case Success(result, next) => Right(result)
    }
}

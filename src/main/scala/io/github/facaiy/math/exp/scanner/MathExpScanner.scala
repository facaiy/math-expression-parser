package io.github.facaiy.math.exp.scanner

import io.github.facaiy.math.exp._
import scala.util.parsing.combinator.JavaTokenParsers


/**
 * Created by facai on 6/6/17.
 */
object MathExpScanner extends JavaTokenParsers {
  def add: Parser[Operator] = "+" ^^ (_ => ADD)
  def minus: Parser[Operator] = "-" ^^ (_ => MINUS)
  def multiply: Parser[Operator] = "*" ^^ (_ => MULTIPLY)
  def divide: Parser[Operator] = "/" ^^ (_ => DIVIDE)

  def comma: Parser[Delimiter] = "," ^^ (_ => COMMA)
  def leftParenthesis: Parser[Delimiter] = "(" ^^ (_ => LEFT_PARENTHESIS)
  def rightParenthesis: Parser[Delimiter] = ")" ^^ (_ => RIGHT_PARENTHESIS)

  def integer: Parser[INTEGER] = """-?\d+""".r ^^ (x => INTEGER(x.toInt))

  def float: Parser[FLOAT] = """-?(\d+\.(\d*)?|\d*\.\d+)""".r ^^ (x => FLOAT(x.toDouble))

  def variable: Parser[VARIABLE] = "$" ~ ident ^^ {
    case _ ~ n => VARIABLE(n)
  }

  def function: Parser[FUNC_NAME] = ident ^^ (n => FUNC_NAME(n))

  def tokens: Parser[List[MathExpToken]] = {
    phrase(rep1(add | multiply | divide |
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

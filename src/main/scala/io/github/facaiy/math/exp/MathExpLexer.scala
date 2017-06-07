package io.github.facaiy.math.exp

import scala.util.parsing.combinator.JavaTokenParsers


/**
 * Created by facai on 6/6/17.
 */
object MathExpLexer extends JavaTokenParsers {
  def add: Parser[Operator] = "+" ^^ (_ => ADD)
  def minus: Parser[Operator] = "-" ^^ (_ => MINUS)
  def multiply: Parser[Operator] = "*" ^^ (_ => MULTIPLY)
  def divide: Parser[Operator] = "/" ^^ (_ => DIVIDE)

  def comma: Parser[Delimiter] = "," ^^ (_ => COMMA)
  def leftParenthesis: Parser[Delimiter] = "(" ^^ (_ => LEFT_PARENTHESIS)
  def rightParenthesis: Parser[Delimiter] = ")" ^^ (_ => RIGHT_PARENTHESIS)

  def integer: Parser[INTEGER] = wholeNumber ^^ (x => INTEGER(x.toInt))
  def float: Parser[FLOAT] = floatingPointNumber ^^ (x => FLOAT(x.toDouble))

  def variable: Parser[VARIABLE] = "$" ~ stringLiteral ^^ {
    case _ ~ x => VARIABLE(x)
  }

  def function: Parser[FUNCTION] = stringLiteral ^^ {n => FUNCTION(n)}

  def tokens: Parser[List[MathExpToken]] = {
    phrase(rep1(add | minus | multiply | divide |
                comma | leftParenthesis | rightParenthesis |
                integer | float |
                variable | function))
  }
}
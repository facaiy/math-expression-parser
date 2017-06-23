package io.github.facaiy.math.expression

import io.github.facaiy.math.expression.compiler.MathExpCompiler

/**
 * Created by facai on 6/19/17.
 */
sealed trait MathExpError
case class MathExpScannerError(msg: String) extends MathExpError
case class MathExpParserError(msg: String) extends MathExpError

object MathExp {
  def parse(s: String): Expression[String => Double, Double] =
    MathExpCompiler(s) match {
      case Right(ts) => Expression.toExpression(ts)
      case Left(e) => throw new IllegalArgumentException(e.toString)
    }
}

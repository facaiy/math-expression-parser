package io.github.facaiy.math.expression

import io.github.facaiy.math.expression.Operations.Operators
import io.github.facaiy.math.expression.compiler.MathExpCompiler
import scala.reflect.ClassTag

/**
 * Created by facai on 6/19/17.
 */
sealed trait MathExpError
case class MathExpScannerError(msg: String) extends MathExpError
case class MathExpParserError(msg: String) extends MathExpError

object MathExp {
  def parse[T: ClassTag: Operators](s: String): Expression[String => T, T] = {
    MathExpCompiler(s) match {
      case Right(ts) => Expression.toExpression[T](ts)
      case Left(e) => throw new IllegalArgumentException(e.toString)
    }
  }
}

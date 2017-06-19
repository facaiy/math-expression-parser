package io.github.facaiy.math.exp

import io.github.facaiy.math.exp.generator.Expression
import io.github.facaiy.math.exp.parser.WorkflowCompiler

/**
 * Created by facai on 6/19/17.
 */
object MathExp {
  def parse(s: String): Expression[String => Double, Double] =
    WorkflowCompiler(s) match {
      case Right(ts) => Expression.toExpression(ts)
      case Left(e) => throw new IllegalArgumentException(e.toString)
    }
}
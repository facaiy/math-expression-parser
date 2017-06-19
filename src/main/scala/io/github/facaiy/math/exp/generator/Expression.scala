package io.github.facaiy.math.exp.generator

import io.github.facaiy.math.exp.parser._

/**
 * Created by facai on 6/19/17.
 */
case class Expression[A, B](f: A => B) {
  def map2[C, D](that: Expression[A, C])(g: (B, C) => D): Expression[A, D] =
    Expression(x => g(this.f(x), that.f(x)))

  def map[C](g: B => C): Expression[A, C] = Expression(g compose f)
}

object Expression {
  val function1: Map[String, Double => Double] = Map(
    "sqrt" -> Math.sqrt,
    "log" -> Math.log,
    "log10" -> Math.log10,
    "log1p" -> Math.log1p,
    "abs" -> Math.abs
  )

  val function2: Map[String, (Double, Double) => Double] = Map(
    "+" -> (_+_),
    "-" -> (_-_),
    "*" -> (_*_),
    "/" -> (_/_),
    "**" -> ((x, y) => Math.pow(x, y.toInt)))

  def toExpression(ast: MathExpAST): Expression[String => Double, Double] = ast match {
    case Constant(d: Double) => Expression(_ => d)
    case c @ Constant(_) => throw new IllegalArgumentException(c.toString)
    case Variable(n) => Expression(f => f(n))
    case Operator2(op, v1, v2) =>
      toExpression(v1).map2(toExpression(v2))(function2(op))
    case f @ OperatorN(op, as) =>
      val args = sequence(as.map(toExpression)).map(_.toArray)
      args.map{ xs: Array[Double] =>
        xs.size match {
          case 1 => function1(op)(xs.head)
          case 2 => function2(op)(xs.head, xs(2))
          case _ => throw new UnsupportedOperationException(f.toString)
        }
      }
  }

  def unit[A, B](b: => B): Expression[A, B] = Expression(_ => b)

  def sequence[A, B](ls: List[Expression[A, B]]): Expression[A, List[B]] =
    ls.foldRight(unit[A, List[B]](List.empty))(
                 (e, acc) => Expression(x => e.f(x) :: acc.f(x)))
}
package io.github.facaiy.math.expression

import io.github.facaiy.math.expression.compiler.parser._

/**
 * Created by facai on 6/19/17.
 */
case class Expression[A, B](eval: A => B) {
  def map2[C, D](that: Expression[A, C])(g: (B, C) => D): Expression[A, D] =
    Expression(x => g(this.eval(x), that.eval(x)))

  def map[C](g: B => C): Expression[A, C] = Expression(g compose eval)
}

object Expression {
  import FunctionRegister._

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
                 (e, acc) => Expression(x => e.eval(x) :: acc.eval(x)))
}

object FunctionRegister {
  val function1: Map[String, Double => Double] = Map(
    // scala.math
    // Rounding
    "ceil" -> Math.ceil,
    "floor" -> Math.floor,
    "rint" -> Math.rint,
    "round" -> ((x: Double) => Math.round(x)).andThen(_.toDouble),
    // Exponential and Logarithmic
    "exp" -> Math.exp,
    "expm1" -> Math.expm1,
    "log" -> Math.log,
    "log10" -> Math.log10,
    "log1p" -> Math.log1p,
    // Trigonometric
    "acos" -> Math.acos,
    "asin" -> Math.asin,
    "atan" -> Math.atan,
    "cos" -> Math.cos,
    "sin" -> Math.sin,
    "tan" -> Math.tan,
    // Angular Measurement Conversion
    "toDegrees" -> Math.toDegrees,
    "toRadians" -> Math.toRadians,
    // Hyperbolic
    "cosh" -> Math.cosh,
    "sinh" -> Math.sinh,
    "tanh" -> Math.tanh,
    // Absolute Values
    "abs" -> Math.abs,
    // Signs
    "signum" -> Math.signum,
    // Root Extraction
    "cbrt" -> Math.cbrt,
    "sqrt" -> Math.sqrt,
    // Unit of Least Precision
    "ulp" -> Math.ulp
  )

  val function2: Map[String, (Double, Double) => Double] = Map(
    "+" -> (_ + _),
    "-" -> (_ - _),
    "*" -> (_ * _),
    "/" -> (_ / _),
    // scala.math
    // Minimum and Maximum
    "max" -> Math.max,
    "min" -> Math.min,
    // Exponential and Logarithmic
    "**" -> Math.pow,
    "pow" -> Math.pow,
    // Polar Coordinates
    "atan2" -> Math.atan2,
    "hypot" -> Math.hypot
  )
}

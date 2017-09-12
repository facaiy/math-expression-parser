package io.github.facaiy.math.expression

/**
 * Created by fshao on 9/5/17.
 */
object Operations {
  trait Operators[T] {
    def unaryOps: Map[String, T => T]
    def binaryOps: Map[String, (T, T) => T]
  }
  object Operators {
    implicit object DoubleOps extends Operators[Double] {
      val unaryOps: Map[String, Double => Double] = Map(
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

      val binaryOps: Map[String, (Double, Double) => Double] = Map(
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
  }
}
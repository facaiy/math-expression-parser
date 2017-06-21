package io.github.facaiy.math.expression.compiler.parser

import io.github.facaiy.math.expression.compiler.MathExpCompiler
import io.github.facaiy.math.expression.compiler.scanner._
import org.scalatest.FunSpec

/**
 * Created by facai on 6/9/17.
 */
class MathExpParserSuite extends FunSpec {
  describe("For math expression") {
    it("parse string to tokens correctly") {
      val expression = "1.0 ** 2 + 2 * $data - power(2, 10) / 4."

      val tokens = MathExpCompiler(expression)

      assert(tokens === Right(
        Operator2("-",
          Operator2("+", Operator2("**", Constant(1.0), Constant(2.0)),
                         Operator2("*", Constant(2.0), Variable("data"))),
          Operator2("/", OperatorN("power", List(Constant(2.0), Constant(10.0))), Constant(4.0)))))
    }
  }
}

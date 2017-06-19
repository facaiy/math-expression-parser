package io.github.facaiy.math.exp.generator

import io.github.facaiy.math.exp.parser.WorkflowCompiler
import org.scalatest.FunSpec

/**
 * Created by facai on 6/19/17.
 */
class ExpressionSuite extends FunSpec {
  describe("For math expression") {
    it("toExpression") {
      import Expression._

      val expression = "1.0 + sqrt(2 * $a1) + $a2 ** 2"

      val tokens = WorkflowCompiler(expression)

      tokens match {
        case Right(ts) =>
          val e = toExpression(ts)
          assert(e.eval(Map("a1" -> 0.0, "a2" -> 0)) === 1)
          assert(e.eval(Map("a1" -> 2.0, "a2" -> 1)) === 4)
          assert(e.eval(Map("a1" -> 8.0, "a2" -> 2)) === 9)
      }
    }
  }
}

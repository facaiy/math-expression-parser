package io.github.facaiy.math.exp.parser

import io.github.facaiy.math.exp.scanner._
import org.scalatest.FunSpec

/**
 * Created by facai on 6/9/17.
 */
class MathExpParserSuite extends FunSpec {
  describe("For math expression") {
    it("parse string to tokens correctly") {
      val expression = "1.0 + 2 * $data - power(2, 10) / 4."
      // val expression = "1 + 2.2 * 3 ^ 6 - power(2 + 3 * 5, 10) / 4"
      // val expression = "power(2, 10, 3)"

      val tokens = MathExpScanner(expression)
      println(tokens)

      tokens match {
        case Right(t) =>
          val res = MathExpParser(t)
          println(res)
      }
    }
  }
}

package io.github.facaiy.math.exp.parser

import io.github.facaiy.math.exp.scanner._
import org.scalatest.FunSpec

/**
 * Created by facai on 6/9/17.
 */
class MathExpParserSuite extends FunSpec {
  describe("For math expression") {
    it("parse string to tokens correctly") {
      // val expression = "1.0 + 2 * $data - power(2, 10) / 4."
      val expression = "1 + 2 * 3 ^ 6 - 5"
      println(WorkflowCompiler(expression))
      // val toekns = Seq(INTEGER(1), ADD, INTEGER(2), MULTIPLY, FLOAT(1.0))
      //println(MathExpParser(toekns))
    }
  }
}

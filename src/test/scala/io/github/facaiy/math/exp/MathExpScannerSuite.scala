package io.github.facaiy.math.exp

import org.scalatest.FunSpec

/**
 * Created by facai on 6/8/17.
 */
class MathExpScannerSuite extends FunSpec {
  describe("For tokens") {
    import MathExpScanner._

    def parse(token: Parser[MathExpToken])
             (expression: String): Either[MathExpScannerError, MathExpToken] = {
      MathExpScanner.parse(token, expression) match {
        case NoSuccess(msg, next) => Left(MathExpScannerError(msg))
        case Success(result, next) => Right(result)
      }
    }

    it("integer") {
      val p = parse(MathExpScanner.integer) _

      assert(p("1") === Right(INTEGER(1)))
      assert(p("0") === Right(INTEGER(0)))
      assert(p("-1") === Right(INTEGER(-1)))
      assert(p("10") === Right(INTEGER(10)))

      assert(p("+1").isLeft)
      assert(p("+1.0").isLeft)
      assert(p("1.0") === Right(INTEGER(1)))
      assert(p("-1.0") === Right(INTEGER(-1)))
    }

    it("float") {
      val p = parse(MathExpScanner.float) _

      assert(p(".1") === Right(FLOAT(0.1)))
      assert(p("1.") === Right(FLOAT(1.0)))
      assert(p("1.0") === Right(FLOAT(1.0)))
      assert(p("-1.0") === Right(FLOAT(-1.0)))
      assert(p("1.02") === Right(FLOAT(1.02)))

      assert(p("1").isLeft)
      assert(p("+1.0").isLeft)
    }

    it("variable") {
      val p = parse(MathExpScanner.variable) _

      assert(p("$hello") === Right(VARIABLE("hello")))
      assert(p("hello").isLeft)
      assert(p("$1hello").isLeft)
    }
  }

  describe("For math expression") {
    it("parse string to tokens correctly") {
      val expression = "1.0 + 2 * $data - power(2, 10) / 4."
      assert(
        MathExpScanner(expression) ===
        Right(
          List(FLOAT(1.0), ADD, INTEGER(2), MULTIPLY, VARIABLE("data"), MINUS,
            FUNCTION("power"), LEFT_PARENTHESIS, INTEGER(2), COMMA, INTEGER(10), RIGHT_PARENTHESIS,
            DIVIDE, FLOAT(4.0))))
    }
  }
}

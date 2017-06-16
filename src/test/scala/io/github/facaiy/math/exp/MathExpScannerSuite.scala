package io.github.facaiy.math.exp

import io.github.facaiy.math.exp.scanner._
import org.scalatest.FunSpec

/**
 * Created by facai on 6/8/17.
 */
class MathExpScannerSuite extends FunSpec {
  describe("For tokens") {
    import io.github.facaiy.math.exp.scanner.MathExpScanner._

    def parse(token: Parser[MathExpToken])
             (expression: String): Either[MathExpScannerError, MathExpToken] = {
      MathExpScanner.parse(token, expression) match {
        case NoSuccess(msg, next) => Left(MathExpScannerError(msg))
        case Success(result, next) => Right(result)
      }
    }

    it("float") {
      val p = parse(MathExpScanner.number) _

      // Float
      assert(p(".1") === Right(NUMBER(0.1)))
      assert(p("1.") === Right(NUMBER(1)))
      assert(p("1f") === Right(NUMBER(1)))
      assert(p("1.0") === Right(NUMBER(1)))
      assert(p("-1.0") === Right(NUMBER(-1.0)))
      assert(p("1.02") === Right(NUMBER(1.02)))

      // Int
      assert(p("1") === Right(NUMBER(1)))

      // Long
      assert(p("1L") === Right(NUMBER(1)))

      // Invaild
      assert(p("+1.0").isLeft)
    }

    it("variable") {
      val p = parse(MathExpScanner.variable) _

      assert(p("$hello") === Right(VAR_NAME("hello")))
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
          List(NUMBER(1), ADD, NUMBER(2), MULTIPLY, VAR_NAME("data"), MINUS,
               FUNC_NAME("power"), LEFT_PARENTHESIS, NUMBER(2), COMMA, NUMBER(10), RIGHT_PARENTHESIS,
               DIVIDE, NUMBER(4))))
    }
  }
}

package io.github.facaiy.math.expression.compiler

import io.github.facaiy.math.expression.MathExpError
import io.github.facaiy.math.expression.compiler.parser.{MathExpAST, MathExpParser}
import io.github.facaiy.math.expression.compiler.scanner.MathExpScanner

/**
 * Created by facai on 6/21/17.
 */
object MathExpCompiler {
  def apply(code: String): Either[MathExpError, MathExpAST] =
    for {
      tokens <- MathExpScanner(code).right
      ast <- MathExpParser(tokens).right
    } yield ast
}

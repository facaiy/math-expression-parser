package io.github.facaiy.math.exp.parser

import io.github.facaiy.math.exp.scanner.Value

/**
 * Created by facai on 6/8/17.
 */
sealed trait MathExpAST

case class CONSTANT(v: Value) extends MathExpAST
case class VARIABLE(v: )

case class FUNCTION2(op: String, v1: Value, v2: Value) extends MathExpAST
case class FUNCTION(op: String, vs: List[Value]) extends MathExpAST

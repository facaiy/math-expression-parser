package io.github.facaiy.math.exp.parser

import io.github.facaiy.math.exp.scanner.{Value, VAR_NAME}

/**
 * Created by facai on 6/8/17.
 */
sealed trait MathExpAST

case class Constant[+A](get: A) extends MathExpAST {
  override def toString: String = get.toString
}

case class Variable(name: String) extends MathExpAST

case class Operator2(op: String, v1: MathExpAST, v2: MathExpAST) extends MathExpAST {
  override def toString: String = s"($op, $v1, $v2)"
}
case class OperatorN(op: String, vs: List[MathExpAST]) extends MathExpAST

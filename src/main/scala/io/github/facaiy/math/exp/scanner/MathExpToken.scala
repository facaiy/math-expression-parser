package io.github.facaiy.math.exp.scanner

/**
 * Created by facai on 6/7/17.
 */
sealed trait MathExpToken

sealed trait Operator extends MathExpToken
case object ADD extends Operator
case object MINUS extends Operator
case object MULTIPLY extends Operator
case object DIVIDE extends Operator

sealed trait Delimiter extends Operator
case object COMMA extends Delimiter
case object LEFT_PARENTHESIS extends Delimiter
case object RIGHT_PARENTHESIS extends Delimiter

sealed trait Value extends MathExpToken
case class INTEGER(value: Int) extends Value
case class FLOAT(value: Double) extends Value

case class VAR_NAME(name: String) extends MathExpToken

case class FUNC_NAME(name: String) extends MathExpToken

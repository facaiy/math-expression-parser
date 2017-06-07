package io.github.facaiy.math.exp

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

case class INTEGER(value: Int) extends MathExpToken
case class FLOAT(value: Double) extends MathExpToken

case class VARIABLE(name: String) extends MathExpToken
case class FUNCTION(name: String) extends MathExpToken

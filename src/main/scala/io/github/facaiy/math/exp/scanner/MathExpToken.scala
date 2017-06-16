package io.github.facaiy.math.exp.scanner

/**
 * Created by facai on 6/7/17.
 */
sealed trait MathExpToken

sealed trait Operator extends MathExpToken
case object ADD extends Operator {
  override def toString: String = "+"
}
case object MINUS extends Operator {
  override def toString: String = "-"
}
case object MULTIPLY extends Operator {
  override def toString: String = "*"
}
case object DIVIDE extends Operator {
  override def toString: String = "/"
}
case object POWER extends Operator {
  override def toString: String = "**"
}

sealed trait Delimiter extends Operator
case object COMMA extends Delimiter {
  override def toString: String = ","
}
case object LEFT_PARENTHESIS extends Delimiter {
  override def toString: String = "("
}
case object RIGHT_PARENTHESIS extends Delimiter {
  override def toString: String = ")"
}

sealed trait Value extends MathExpToken
case class NUMBER(value: Double) extends Value {
  override def toString: String = value.toString
}

case class VAR_NAME(name: String) extends MathExpToken

case class FUNC_NAME(name: String) extends MathExpToken

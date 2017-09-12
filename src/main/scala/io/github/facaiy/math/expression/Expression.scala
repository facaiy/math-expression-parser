package io.github.facaiy.math.expression

import io.github.facaiy.math.expression.compiler.parser._
import scala.reflect.ClassTag

/**
 * Created by facai on 6/19/17.
 */
case class Expression[A, B](eval: A => B) {
  def map2[C, D](that: Expression[A, C])(g: (B, C) => D): Expression[A, D] =
    Expression(x => g(this.eval(x), that.eval(x)))

  def map[C](g: B => C): Expression[A, C] = Expression(g compose eval)
}

object Expression {
  import Operations.Operators

  def toExpression[T: ClassTag : Operators](ast: MathExpAST): Expression[String => T, T] = ast match {
    case Constant(d: T) => Expression(_ => d)
    case c @ Constant(_) => throw new IllegalArgumentException(c.toString)
    case Variable(n) => Expression(f => f(n))
    case Operator2(op, v1, v2) =>
      val a: Expression[(String) => T, T] = toExpression[T](v1)
      a.map2[T, T](toExpression[T](v2))(implicitly[Operators[T]].binaryOps(op))

    case f @ OperatorN(op, as) =>
      val a: Expression[(String) => T, List[T]] = sequence[(String) => T, T](as.map(toExpression[T] _))
        val args: Expression[(String) => T, Array[T]] = a.map(d => d.toArray[T])
      args.map{ xs: Array[T] =>
        xs.size match {
          case 1 => implicitly[Operators[T]].unaryOps(op)(xs.head)
          case 2 => implicitly[Operators[T]].binaryOps(op)(xs.head, xs(2))
          case _ => throw new UnsupportedOperationException(f.toString)
        }
      }
  }

  def unit[A, B](b: => B): Expression[A, B] = Expression(_ => b)

  def sequence[A, B](ls: List[Expression[A, B]]): Expression[A, List[B]] =
    ls.foldRight(unit[A, List[B]](List.empty))(
                 (e, acc) => Expression(x => e.eval(x) :: acc.eval(x)))
}


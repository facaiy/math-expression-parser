package io.github.facaiy.math.exp

/**
 * Created by facai on 6/8/17.
 */
sealed trait MathExpError

case class MathExpScannerError(msg: String) extends MathExpError

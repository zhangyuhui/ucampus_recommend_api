package com.ucampus.tools

object NumberOp {
  def truncateAt(n: Double, p: Int): Double = {
    val s = math pow(10, p)
    (math floor n * s) / s
  }

  def roundAt(p: Int)(n: Double): Double = {
    val s = math pow(10, p)
    (math round n * s) / s
  }

}

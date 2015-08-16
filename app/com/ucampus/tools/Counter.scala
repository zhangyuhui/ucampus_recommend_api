package com.ucampus.tools

case class IntCounter(initialValue: Int = 0) {
  private var n = initialValue

  def ++(): Int = {
    val current = n
    n += 1
    current
  }

  def --(): Int = {
    val current = n
    n -= 1
    current
  }

  def get: Int = n

  override def toString = n.toString
}
package com.moviedemo.tools

object StringOp {
  implicit def sensitize(c: CaseInsensitive) = c.s

  implicit def desensitize(s: String) = CaseInsensitive(s)

  case class CaseInsensitive(s: String) extends Proxy with Ordered[CaseInsensitive] {
    val self: String = s.toLowerCase

    def compare(other: CaseInsensitive) = self compareTo other.self

    override def toString = s

    def i = this // convenience implicit conversion
  }

  def formatNumber(number: Double) = {
    if (number.isValidInt) number.toInt.toString
    else number.toString
  }

  implicit class OptionStringExtensions(item : Option[String]){
    def isNullOrEmpty = item == null || item.isEmpty || item.get.isNullOrEmpty
  }

  implicit class StringExtensions(item : String){
    def isNullOrEmpty = item == null || item.trim.isEmpty
  }
}
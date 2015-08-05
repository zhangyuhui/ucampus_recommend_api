package com.moviedemo.mvc

import scala.reflect.ClassTag

case class ServiceResult[T](value: T, messages: List[String] = Nil) {

  def hasErrors(): Boolean = {
    !messages.isEmpty
  }
}

case class EnumServiceResultValue(id: Int, name: String)

class EnumServiceResult[T] private (val values: Seq[EnumServiceResultValue])

object EnumServiceResult {
  def apply[E](getId: E => Int, getName: E => String)(implicit ct: ClassTag[E]): EnumServiceResult[E] = {
    ct.runtimeClass match {
      case c: Class[E] if c.isEnum => new EnumServiceResult(c.getEnumConstants.map(x => EnumServiceResultValue(getId(x), getName(x))).toSeq)
      case c => sys.error(s"${c.getName} is not an enum type")
    }
  }

  def apply[E <: {def getId(): Int; def getDescription(): String}]()(implicit ct: ClassTag[E]): EnumServiceResult[E] = apply[E]({x: E => x.getId}, {x: E => x.getDescription})
}
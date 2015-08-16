package com.ucampus.tools

case class ParseOp[T](op: String => T)

object  ParseOp{
  implicit val popInt = ParseOp[Int](_.toInt)
  implicit val popDouble = ParseOp[Double](_.toDouble)
  implicit val popString = ParseOp[String](x=>x)
  implicit val popLong = ParseOp[Long](_.toLong)

  def parse[T: ParseOp](s: String) = try { Some(implicitly[ParseOp[T]].op(s)) }
  catch {case _:Throwable => None}

  def canParse[T: ParseOp](s:String) :Boolean = parse[T](s) != None
}

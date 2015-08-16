package com.ucampus.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object ValidationMacros {
  def impl[T: c.WeakTypeTag](c: Context): c.Expr[Class[_]] = {
    import c.universe._;
    c.Expr[Class[_]](Literal(Constant(weakTypeOf[T])))
  }

  def implClassName[T: c.WeakTypeTag](c: Context): c.Expr[String] = {
    import c.universe._;
    c.Expr[String](Literal(Constant(weakTypeOf[T].toString)))
  }
  def classOfObject[T]: Class[_] = macro impl[T]

  def className[T]: String = macro implClassName[T]

}

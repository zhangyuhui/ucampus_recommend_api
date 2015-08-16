package com.ucampus.tools


import scala.reflect.ClassTag


object ClassOp {
  def newInstance[T](args:AnyRef*)(implicit ct : ClassTag[T]):T = {
    def argTypes = args.map(_.getClass)
    def constructor = ct.runtimeClass.getConstructor(argTypes: _*)
    constructor.newInstance(args: _*).asInstanceOf[T]
  }
}

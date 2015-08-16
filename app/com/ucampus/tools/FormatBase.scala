package com.ucampus.tools

import play.api.libs.json.{OWrites, Writes}
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl

trait FormatBase {
  lazy val writesMap: Map[Class[_], Writes[_]] = {
    val result = for {f <- getClass.getDeclaredMethods
                      if classOf[OWrites[_]].isAssignableFrom(f.getReturnType)
                      if f.getGenericReturnType.isInstanceOf[ParameterizedTypeImpl]
    } yield {
      val modelClass = f.getGenericReturnType.asInstanceOf[ParameterizedTypeImpl].getActualTypeArguments.head.asInstanceOf[Class[_]]
      modelClass -> f.invoke(this).asInstanceOf[Writes[_]]
    }

    result.toMap
  }
}

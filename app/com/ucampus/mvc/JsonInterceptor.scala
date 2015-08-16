package com.ucampus.mvc

import play.api.libs.json._
import play.api.mvc.Request
import play.api.libs.json.JsObject

object JsonInterceptor {

  implicit def t1ToList(t: JsonInterceptor) = List(t)

  implicit def t2ToList(t: (JsonInterceptor, JsonInterceptor)) = List(t._1, t._2)

  def readsJson(jsValue: JsValue, interceptors: JsonInterceptor*)(implicit request: Request[_]): JsValue = {
    interceptors.filter(_.canIntercept).foldLeft(jsValue) {
      (jsValue, interceptor) =>
        interceptor.reads(jsValue)
    }
  }

  def writesJson(jsValue: JsValue, interceptors: JsonInterceptor*)(implicit request: Request[_]): JsValue = {
    interceptors.filter(_.canIntercept).foldLeft(jsValue) {
      (jsValue, interceptor) =>
        interceptor.writes(jsValue)
    }
  }

  def format[A](interceptors: JsonInterceptor*)(implicit defaultFormat: Format[A], request: Request[_]): Format[A] = {
    if (interceptors.isEmpty) defaultFormat
    else Format[A](reads(interceptors: _*), writes(interceptors: _*))
  }

  def writes[A](interceptors: JsonInterceptor*)(implicit default: Writes[A], request: Request[_]): Writes[A] = {
    val self = this
    if (interceptors.isEmpty) default
    else new Writes[A] {
      override def writes(o: A): JsValue = {
        self.writesJson(default.writes(o), interceptors: _*)
      }
    }
  }

  def reads[A](interceptors: JsonInterceptor*)(implicit default: Reads[A], request: Request[_]): Reads[A] = {
    val self = this
    if (interceptors.isEmpty) default
    else new Reads[A] {
      override def reads(json: JsValue): JsResult[A] = {
        default.reads(self.readsJson(json, interceptors: _*))
      }
    }
  }
}

trait JsonInterceptor {
  def reads(js: JsValue): JsValue = js

  def writes(js: JsValue): JsValue = js

  def canIntercept(implicit request: Request[_]): Boolean = true
}

class TransformationInterceptor(headerValue: String) extends JsonInterceptor {
  override def canIntercept(implicit request: Request[_]) = {
    val header = request.headers.get("Transformations")
    header.isDefined && header.get.toLowerCase == headerValue
  }

  override def writes(js: JsValue) = writesDefault(js)

  override def reads(js: JsValue) = readsDefault(js)

  def writesDefault(js: JsValue, rootSingle: Option[String] = None, rootMany: Option[String] = None) = readWritesDefault(js, writesSingle, rootSingle, rootMany)

  def readsDefault(js: JsValue, rootSingle: Option[String] = None, rootMany: Option[String] = None) = readWritesDefault(js, readsSingle, rootSingle, rootMany)

  protected def writesSingle(js: JsValue) = js

  protected def readsSingle(js: JsValue) = js

  private def readWritesDefault(js: JsValue, singleFunc: JsValue => JsValue, rootSingle: Option[String] = None, rootMany: Option[String] = None) = {

    val array = (rootSingle, rootMany) match {
      case (_, Some(path)) => js \ path
      case (Some(path), _) => js \ JsonHelper.pluralise(path)
      case _ => js
    }

    lazy val single = rootSingle match {
      case Some(path) => js \ rootSingle.get
      case _ => js
    }

    array match {
      case many: JsArray => JsArray(many.value.map(singleFunc))
      case _ =>
        single match {
          case x : JsUndefined => js
          case jsObj => singleFunc(jsObj)
        }
    }
  }
}


trait PartialInterceptor extends JsonInterceptor {
  override def canIntercept(implicit request: Request[_]) = {
    val header = request.headers.get("X-HTTP-Method-Override")
    header.isDefined && header.get.toLowerCase == "patch"
  }

  def readsDefault[T](js: JsValue, entityFunc: Int => Option[T])(implicit writes: Writes[T]) = {
    lazy val id = (js \ "id").validate[Int].asOpt
    lazy val entity = entityFunc(id.get)

    js match {
      case jsObj: JsObject if id.isDefined && entity.isDefined =>
        Json.toJson(entity.get) match {
          case original: JsObject => original.deepMerge(jsObj)
          case _ => js
        }
      case _ => js
    }
  }
}

trait JsType {
  val path: String
}

class JsObjectType(val path: String) extends JsType

class JsArrayType(val path: String) extends JsType

object JsonMapExtensions {
  implicit class StringExtensions(str: String) {
    def \(other: String): List[JsType] = List(new JsObjectType(str), new JsObjectType(other))

    def \\(other: String): List[JsType] = List(new JsObjectType(str), new JsArrayType(other))

    def ->(other: String): (List[JsType], List[JsType]) = (List(new JsObjectType(str)), List(new JsObjectType(other)))

    def ->\\(other: String): (List[JsType], List[JsType]) = (List(new JsArrayType(str)), List(new JsArrayType(other)))

    def ->(other: List[JsType]): (List[JsType], List[JsType]) = (List(new JsObjectType(str)), other)

    def ->\\(other: List[JsType]): (List[JsType], List[JsType]) = (List(new JsArrayType(str)), other)
  }

  implicit class ListExtensions(list: List[JsType]) {
    def \(other: String) = new JsObjectType(other) :: list

    def \\(other: String) = new JsArrayType(other) :: list

    def ->(other: String): (List[JsType], List[JsType]) = (list, List(new JsObjectType(other)))

    def ->\\(other: String): (List[JsType], List[JsType]) = (list, List(new JsArrayType(other)))

    def ->(other: List[JsType]): (List[JsType], List[JsType]) = (list, other)

    def ->\\(other: List[JsType]): (List[JsType], List[JsType]) = (list, other)
  }

}

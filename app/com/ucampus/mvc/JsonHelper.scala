package com.ucampus.mvc

import play.api.libs.json.{Json => PlayJson}
import play.api.libs.json._
import scala.language.experimental.macros
import play.api.mvc.Results._
import play.api.libs.json.Json.JsValueWrapper

object JsonHelper {

  private val uncountableNames: List[String] = "news" :: Nil

  implicit def from[A](formats: Format[A]): OFormatOps[A] = new OFormatOps(formats)

  def toJsonResult[T](o: ServiceResult[Option[T]], resultForEmpty: => play.api.mvc.Result = NotFound)(implicit tjs: Writes[ServiceResult[Option[T]]]) = {
    if (o.value.isDefined) Ok(Json.toJson(o))
    else resultForEmpty
  }

  def getRecordType[T](pluralise: Boolean = false)(implicit manifest: Manifest[T], recordTypes: Map[Class[_], String]): String = {
    getClassType(pluralise)(manifest.runtimeClass, recordTypes)
  }

  def getClassType(pluralise: Boolean = false)(clazz: Class[_], recordTypes: Map[Class[_], String]): String = {
    val recordType = recordTypes.getOrElse(clazz, null)
    if (recordType != null) {
      recordType
    } else {
      val name = clazz.getSimpleName replace("$", "") // in case .type is provided
      val canonicalName = name.substring(0, 1).toLowerCase + name.substring(1)
      if (pluralise) this.pluralise(canonicalName) else canonicalName
    }
  }

  def pluralise(s: String): String = {
    if (s.endsWith("ey")) {
      s + "s"
    } else if (s.endsWith("y")) {
      s.substring(0, s.length - 1) + "ies"
    } else if (!uncountableNames.contains(s)) {
      s + "s"
    } else {
      s
    }
  }

  def singleRecordToJson[T](o: Option[T])(implicit recordTypes: Map[Class[_], String], tjs: Writes[T], manifest: Manifest[T]): JsValue = {
    val result = Json.toJson(o)
    JsObject(Seq(
      getRecordType[T](false) -> result)
    )
  }
}

class OFormatOps[A](formats: Format[A]) {
  //  def addField[T: Writes](fieldName: String, field: A => T)(implicit fcb: FunctionalCanBuild[Format[T]]): Format[A] = Format(
  //    formats,
  //    (formats ~ (__ \ fieldName).format[T])((a: A) => (a, field(a)))
  //  )

  implicit def applyDefaults = withDefaultField("id", 0)

  def removeField(fieldName: String): OFormat[A] = OFormat(
    (r: JsValue) => {
      formats.reads(r)
    },
    (a: A) => {
      val transformer = (__ \ fieldName).json.prune
      PlayJson.toJson(a)(formats).validate(transformer).get
    }
  )

  def copy(destination: String, nodes: String*): OFormat[A] = OFormat(
    (r: JsValue) => {
      formats.reads(r match {
        case jsObj: JsObject => {
          val newObj = if (nodes.isEmpty) jsObj
          else {
            JsObject(nodes.map(n => (n, jsObj \ n)))
          }

          jsObj +(destination, newObj)
        }
        case _ => r
      })
    },
    (a: A) => {
      formats.writes(a).asInstanceOf[JsObject]
    })

  def merge(node: String): OFormat[A] = OFormat(
    (r: JsValue) => formats.reads(r),
    (a: A) => {
      formats.writes(a).asInstanceOf[JsObject] match {
        case jsObj: JsObject => {
          jsObj \ node match {
            case js: JsObject => {
              (jsObj - node).deepMerge(js)
            }
            case _ => jsObj
          }
        }
        case js => js
      }
    })

  def withDefaultFields(fields: (String, JsValueWrapper)*): OFormat[A] = OFormat(
    (r: JsValue) => {
      formats.reads(r match {
        case jsObj: JsObject => jsObj.deepMerge(Json.obj(fields: _*))
        case _ => r
      })
    },
    (a: A) => {
      formats.writes(a).asInstanceOf[JsObject]
    })

  def withDefaultField[T](fieldName: String, value: T)(implicit writes: Writes[T]): OFormat[A] = OFormat(
    (r: JsValue) => {
      formats.reads(r match {
        case jsObj: JsObject => if (jsObj.keys.contains(fieldName)) jsObj else jsObj +(fieldName, writes.writes(value))
        case _ => r
      })
    },
    (a: A) => {
      formats.writes(a).asInstanceOf[JsObject]
    })
}


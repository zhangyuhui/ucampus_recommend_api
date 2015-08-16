package com.ucampus.data.models

import com.ucampus.tools.FormatBase
import org.apache.commons.codec.binary.Base64
import com.ucampus.mvc.{ServiceResult, Formats}
import play.api.libs.json._
import Json._
import play.api.libs.functional.syntax._

object ModelFormats extends FormatBase {

  //Bytes arrays are transmitted as base64-encoded strings
  implicit object byteArrayFormat extends Format[Array[Byte]] {
    override def reads(json: JsValue) = {
      json match {
        case j: JsString => JsSuccess(Base64.decodeBase64(j.value))
        case _ => JsError("Must be a string")
      }
    }
    override def writes(a: Array[Byte]) = JsString(Base64.encodeBase64String(a))
  }

  import scala.collection.immutable.HashMap

  implicit val recordTypes = HashMap[Class[_], String](

  )

  implicit val courseFormat = Json.format[Course]

  implicit def entityResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[Option[T]]] = Formats.entityResultWrites[T]

  implicit def entityResultWritesNotOption[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[T]] = Formats.entityResultWritesNotOption[T]

  implicit def listResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[List[T]]] = Formats.listResultWrites[T]
}

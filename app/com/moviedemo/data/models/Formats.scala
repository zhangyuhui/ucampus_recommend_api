package com.moviedemo.data.models

import com.moviedemo.tools.FormatBase
import org.apache.commons.codec.binary.Base64
import com.moviedemo.mvc.{ServiceResult, Formats}
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

  implicit val movieFormat = format[Movie]
  implicit val userFormat = format[User]
  implicit val ratingFormat = format[Rating]

  import scala.collection.immutable.HashMap

  implicit val recordTypes = HashMap[Class[_], String](

  )

  implicit def entityResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[Option[T]]] = Formats.entityResultWrites[T]

  implicit def entityResultWritesNotOption[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[T]] = Formats.entityResultWritesNotOption[T]

  implicit def listResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[List[T]]] = Formats.listResultWrites[T]
}

package com.ucampus.mvc

import com.ucampus.tools.FormatBase
import play.api.libs.json._
import scala.reflect.ClassTag
import play.api.libs.functional.syntax._

object Formats extends FormatBase {

  //lets make a map once through lifetime of the app on all formats existing
  private var _writesMap = writesMap

  private var _recordTypesMap = Map[Class[_], String](
    //classOf[AccountPerformance] -> "advertisers"
  )

  implicit def getWritesMap: Map[Class[_], Writes[_]] = _writesMap

  implicit def recordTypesMap: Map[Class[_], String] = _recordTypesMap

  def registerWrites(formats: Map[Class[_], Writes[_]]) {
    _writesMap = _writesMap ++ formats
  }

  def registerRecordTypes(recordTypes: Map[Class[_], String]) {
    _recordTypesMap = _recordTypesMap ++ recordTypes
  }

  implicit val enumValueFormat = Json.format[EnumServiceResultValue]

  def getJsonMap[R: ClassTag](map: (Any, List[R])) = {
    val (key, clazz) = map._1 match {
      case c: Class[_] => (JsonHelper.getClassType(pluralise = true)(c, recordTypesMap), c)
      case x => (x.toString, map._2.head.getClass)
    }
    val write = getWritesMap.get(clazz)
    if (write.isEmpty) throw new RuntimeException(s"Unable to find serialization format for class ${clazz.getName}. Make sure you define one in the json object.")
    key -> JsArray(map._2.map(Json.toJson(_)(write.get.asInstanceOf[Writes[R]])))
  }

  implicit def entityResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[Option[T]]] = new Writes[ServiceResult[Option[T]]] {
    def writes(ts: ServiceResult[Option[T]]) = JsObject(
      Seq(JsonHelper.getRecordType[T](pluralise = false) -> Json.toJson(ts.value)) ++ (if (ts.messages.nonEmpty) Seq("errorMessages" -> Json.toJson(ts.messages)) else Nil))
  }

  implicit def entityResultWritesNotOption[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[T]] = new Writes[ServiceResult[T]] {
    def writes(ts: ServiceResult[T]) = JsObject(
      Seq(JsonHelper.getRecordType[T](pluralise = false) -> Json.toJson(ts.value)) ++ (if (ts.messages.nonEmpty) Seq("errorMessages" -> Json.toJson(ts.messages)) else Nil))
  }

  implicit def listResultWrites[T](implicit fmt: Writes[T], manifest: Manifest[T]): Writes[ServiceResult[List[T]]] = new Writes[ServiceResult[List[T]]] {
    def writes(ts: ServiceResult[List[T]]) = JsObject(
      Seq(JsonHelper.getRecordType[T](pluralise = true) -> JsArray(ts.value.map(Json.toJson(_)))) ++ (if (ts.messages.nonEmpty) Seq("errorMessages" -> Json.toJson(ts.messages)) else Nil)
    )
  }

  implicit def enumResultWrites[T](implicit manifest: Manifest[T]): Writes[EnumServiceResult[T]] = new Writes[EnumServiceResult[T]] {
    override def writes(o: EnumServiceResult[T]): JsValue = JsObject(
      Seq(JsonHelper.getRecordType[T](pluralise = true) -> Json.toJson(o.values))
    )
  }
}

package com.ucampus.mvc

import play.api.libs.json.JsString
import play.api.mvc.{AnyContent, Request}

import scala.util.DynamicVariable
import com.ucampus.mvc.RequestHelper._

object RequestOps {
  val requestContext = new DynamicVariable[RequestContext](new RequestContext())

  implicit def stringToParam(key: String): RequestParameter = RequestParameter(key)

  implicit def stringToInt(s: String)(implicit request: Request[Any]): Int = getParamAsInt(s) getOrElse 0

  implicit def stringToLong(s: String)(implicit request: Request[Any]): Long = getParamAsNumber[Long](s) getOrElse 0

  implicit def stringToOptionInt(s: String)(implicit request: Request[Any]): Option[Int] = getParamAsInt(s)

  implicit def stringToBoolean(s: String)(implicit request: Request[Any]): Boolean = getParamAsBoolean(s) getOrElse false

  implicit def stringToOptionBoolean(s: String)(implicit request: Request[Any]): Option[Boolean] = getParamAsBoolean(s)

  implicit def paramToString(p: RequestParameter)(implicit request: Request[Any]): String = getParamAsString(p.key)

  implicit def paramToOptionString(p: RequestParameter)(implicit request: Request[Any]): Option[String] = {
    val s = getParamAsString(p.key)
    if (s == null) None else Some(s)
  }

  implicit def stringToListInt(key: String)(implicit request: Request[Any]): List[Int] = getParamAsList[Int](key)

  implicit def stringToListString(key: String)(implicit request: Request[Any]): List[String] = getParamAsList[String](key)

  implicit def searchOptions(implicit request: Request[Any]) = {
    SearchOptions(query, sortColumn, sortDirection, pageIndex, pageSize)
  }

  implicit def parseRequestBody(request: Request[AnyContent]): String = {
    if (request.method == "POST") {
      val body = request.body.asJson
      if (body.isDefined) body.get.asInstanceOf[JsString].value else null
    } else {
      null
    }
  }
}

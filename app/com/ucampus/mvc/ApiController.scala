package com.ucampus.mvc

import com.ucampus.data.models.HasId
import play.api.mvc._
import play.api.libs.json._
import RequestOps._
import play.api.libs.json.JsObject
import com.ucampus.tools.ParseOp
import com.ucampus.mvc.Formats._

object ApiController {
  type T1[T <: HasId[Int]] = ApiController[T, T]

  import Results._

  implicit def getErrors(errors: JsError): String = com.ucampus.tools.Conversions.getErrors(errors)

  def logApiRequest(request: Request[_], requestJson: JsValue) =
    play.api.Logger.debug(s"[MOVIEDEMO_API] [Id:${System.currentTimeMillis() / 1000}-${request.id}] [${request}] Request = ${requestJson}")

  def logApiResponse(request: Request[_], resultJson: JsValue) =
    play.api.Logger.debug(s"[MOVIEDEMO_API] [Id:${System.currentTimeMillis() / 1000}-${request.id}] [${request}] Response = ${resultJson}")

  def create[R](func: R => ServiceResult[Int], interceptors: JsonInterceptor*)(implicit request: Request[AnyContent], reads: Reads[R], manifestD: Manifest[R]) = {
    request.body.asJson match {
      case Some(js) => {
        createFromJson[R](js, func, interceptors: _*)
      }
      case _ => BadRequest
    }
  }

  def createFromJson[R](json: JsValue, func: R => ServiceResult[Int], interceptors: JsonInterceptor*)(implicit reads: Reads[R], manifestD: Manifest[R], request: Request[_]) = {
    logApiRequest(request, json)

    Json.fromJson[R](json)(JsonInterceptor.reads[R](interceptors: _*)) match {
      case e: JsError => {
        BadRequest(getErrors(e))
      }
      case entity: JsResult[R] => {
        val result = func(entity.get)
        if (result.messages.isEmpty) {
          Created(Json.toJson(Map(JsonHelper.getRecordType[R]() -> Map("id" -> result.value))))
        } else serviceResultErrorResponse(result)
      }
    }
  }

  def createAllFromJson[R](json: JsValue, func: List[R] => ServiceResult[List[Int]])(implicit reads: Reads[R], manifestD: Manifest[R]) = {
    Json.fromJson[List[R]](json) match {
      case e: JsError => BadRequest(getErrors(e))
      case entities: JsResult[List[R]] => {
        val result = func(entities.get)
        if (result.messages.isEmpty) Created(Json.toJson(Map(JsonHelper.getRecordType[R](true) -> result.value.map(x => Map("id" -> x)))))
        else serviceResultErrorResponse(result)
      }
    }
  }

  def result[R, T](func: R => ServiceResult[T], interceptors: JsonInterceptor*)(implicit request: Request[AnyContent], writes: Writes[ServiceResult[T]], reads: Reads[R], manifestD: Manifest[R]) = {
    request.body.asJson match {
      case Some(js) => {
        val writesFormat = JsonInterceptor.writes[ServiceResult[T]](interceptors: _*)
        val readsFormat = JsonInterceptor.reads[R](interceptors: _*)
        // note: all case params must have values.  string = null is not valid
        Json.fromJson[R](js)(readsFormat) match {
          case e: JsError => BadRequest(getErrors(e))
          case entity: JsResult[R] => {
            val result = func(entity.get)
            if (result.messages.isEmpty) Ok(Json.toJson(result)(writesFormat))
            else UnprocessableEntity(Json.toJson(result)(writesFormat))
          }
        }
      }
      case _ => BadRequest
    }
  }

  def update[R](func: R => ServiceResult[_], interceptors: JsonInterceptor*)(implicit request: Request[AnyContent], reads: Reads[R], manifestD: Manifest[R]) = {
    request.body.asJson match {
      case Some(js) => updateFromJson(js, func, interceptors: _*)
      case _ => BadRequest
    }
  }

  def edit[R](func: => ServiceResult[Option[R]], interceptors: JsonInterceptor*)(implicit request: Request[AnyContent], writes: Writes[ServiceResult[Option[R]]]) = {
    val result = func
    if (result.value.isDefined) {
      val resultJson = Json.toJson(result)(JsonInterceptor.writes[ServiceResult[Option[R]]](interceptors: _*))
      logApiResponse(request, resultJson)
      Ok(resultJson)
    } else {
      NotFound
    }
  }

  def index[R](func: => ServiceResult[List[R]], interceptors: JsonInterceptor*)(implicit request: Request[AnyContent], writes: Writes[ServiceResult[List[R]]]) = {
    val result = func
    val resultJson = Json.toJson(result)(JsonInterceptor.writes[ServiceResult[List[R]]](interceptors: _*))
    ApiController.logApiResponse(request, resultJson)
    Ok(resultJson)
  }

  def updateFromJson[R](json: JsValue, func: R => ServiceResult[_], interceptors: JsonInterceptor*)(implicit request: Request[_], reads: Reads[R], manifestD: Manifest[R]) = {
    logApiRequest(request, json)
    Json.fromJson[R](json)(JsonInterceptor.reads[R](interceptors: _*)) match {
      case e: JsError => BadRequest(getErrors(e))
      case entity: JsResult[R] => {
        val objId = ParseOp.parse[Int](request.uri.split("/").last)
        entity.get match {
          case x: HasId[_] if objId.isDefined && x.id != objId.get => BadRequest(getErrors(JsError(s"ID on path doesn't match request payload")))
          case _ => func(entity.get) match {
            case result if !result.messages.isEmpty => serviceResultErrorResponse(result)
            case _ => NoContent
          }
        }
      }
    }
  }

  def serviceResultErrorResponse(result: ServiceResult[_]) = {
    UnprocessableEntity(Json.toJson(Map("errorMessages" -> result.messages)))
  }
}

abstract class ApiController[T <: HasId[Int], D <: HasId[Int]](service: ServiceBase[T, D])
      (implicit val formatT: Format[T], formatD: Format[D], resultFormat: Format[List[T]], manifestT: Manifest[T], manifestD: Manifest[D])
      extends AuthenticateController {

  def index(): EssentialAction = index(Nil)

  def create(): EssentialAction = create(Nil)

  def show(id: Int): EssentialAction = show(id, Nil)

  def edit(id: Int): EssentialAction = edit(id, Nil)

  def update(id: Int): EssentialAction = update(id, Nil)

  def index(interceptors: List[JsonInterceptor] = Nil): EssentialAction = authenticate {
    implicit request: Request[AnyContent] => ApiController.index[T](service.index(searchOptions), interceptors: _*)
  }

  def show(id: Int, interceptors: List[JsonInterceptor] = Nil): EssentialAction = authenticate {
    implicit request: Request[AnyContent] => ApiController.edit[T](service.show(id), interceptors: _*)
  }

  def edit(id: Int, interceptors: List[JsonInterceptor] = Nil): EssentialAction = authenticate {
    implicit request: Request[AnyContent] => ApiController.edit[D](service.edit(id), interceptors: _*)
  }

  def create(interceptors: List[JsonInterceptor] = Nil): EssentialAction = authenticate {
    implicit request: Request[AnyContent] => ApiController.create[D](entity => service.create(entity), interceptors: _*)
  }

  def update(id: Int, interceptors: List[JsonInterceptor] = Nil): EssentialAction = authenticate {
    implicit request: Request[AnyContent] => ApiController.update[D](entity => service.update(entity), interceptors: _*)
  }

  def delete(id: Int): EssentialAction = authenticate {
    implicit request: Request[AnyContent] =>
      service.remove(id)
      NoContent
  }
}





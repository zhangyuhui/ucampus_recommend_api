package com.ucampus.mvc

import play.api.mvc._
import play.api.libs.json._

class AuthenticateController extends Controller {
  implicit def toAnyContentRequest(request: AuthenticateRequest[JsValue]): Request[AnyContent] = Request(request, AnyContentAsJson(request.body))

  def authenticate(block: Request[AnyContent] => Result): EssentialAction = Action(block)

  def authenticate[A](bodyParser: BodyParser[A])(block: Request[A] => Result): EssentialAction = Action.apply(bodyParser)(block)
}
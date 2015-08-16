package com.ucampus.mvc

import play.api.mvc.{EssentialAction, Action}

object OptionsController extends play.api.mvc.Controller {
  def index(): EssentialAction = Action { request =>
    Ok("").withHeaders(
      "Access-Control-Allow-Credentials" -> "true",
      "Access-Control-Allow-Origin" -> request.headers.get("Origin").getOrElse(""),
      "Access-Control-Allow-Methods" -> "GET, POST, PUT, DELETE, OPTIONS",
      "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept, Host, Api-Token",
      "Access-Control-Max-Age" -> (60 * 60 * 24).toString
    )
  }
  def catchAll(path: String): EssentialAction = index
}


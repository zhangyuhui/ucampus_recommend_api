package com.ucampus.tools

import com.google.inject.Inject
import play.api.mvc._

trait AppLogger {
  def trace(message: => String)(implicit request: RequestHeader)

  def trace(message: => String, error: => Throwable)(implicit request: RequestHeader)

  def debug(message: => String)(implicit request: RequestHeader)

  def debug(message: => String, error: => Throwable)(implicit request: RequestHeader)

  def info(message: => String)(implicit request: RequestHeader)

  def info(message: => String, error: => Throwable)(implicit request: RequestHeader)

  def warn(message: => String)(implicit request: RequestHeader)

  def warn(message: => String, error: => Throwable)(implicit request: RequestHeader)

  def error(message: => String)(implicit request: RequestHeader)

  def error(message: => String, error: => Throwable)(implicit request: RequestHeader)
}

class LoggerImpl@Inject()(cache : Cache) extends AppLogger {
  def trace(message: => String)(implicit request: RequestHeader) = play.Logger.trace(message)

  def trace(message: => String, error: => Throwable)(implicit request: RequestHeader) = play.Logger.trace(buildMessage(message, error))

  def debug(message: => String)(implicit request: RequestHeader) = play.Logger.debug(buildMessage(message))

  def debug(message: => String, error: => Throwable)(implicit request: RequestHeader) = play.Logger.debug(buildMessage(message, error))

  def info(message: => String)(implicit request: RequestHeader) = play.Logger.info(buildMessage(message))

  def info(message: => String, error: => Throwable)(implicit request: RequestHeader) = play.Logger.info(buildMessage(message, error))

  def warn(message: => String)(implicit request: RequestHeader) = play.Logger.warn(buildMessage(message))

  def warn(message: => String, error: => Throwable)(implicit request: RequestHeader) = play.Logger.warn(buildMessage(message, error))

  def error(message: => String)(implicit request: RequestHeader) = play.Logger.error(buildMessage(message))

  def error(message: => String, error: => Throwable)(implicit request: RequestHeader) = play.Logger.error(buildMessage(message, error))

  def buildMessage(message: String, error: => Throwable = null)(implicit request: RequestHeader): String = {
    val sb = new StringBuilder()
    val newLine = "\n"

    val remoteAddress = request.remoteAddress
    val token = cache.get[(String, Int)](request.headers.get("Api-Token").getOrElse(""))
    val uri = request.uri

    sb.append(s"IP Address - $remoteAddress $newLine")
    sb.append(s"Token - $token $newLine")
    sb.append(s"Message - $message $newLine")

    if(error != null){
      sb.append(s"Inner Error - $error $newLine")
    }

    sb.append(s"Uri - $uri $newLine")
    sb.toString()
  }
}
package com.moviedemo.mvc

import play.api.mvc.RequestHeader

case class RequestContext(httpRequest: Option[RequestHeader] = None)

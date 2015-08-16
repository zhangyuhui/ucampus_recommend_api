package com.ucampus.mvc

import play.api.mvc.{WrappedRequest, Request}

case class AuthenticateRequest[A](request: Request[A]) extends WrappedRequest(request)

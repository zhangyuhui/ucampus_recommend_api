package com.ucampus.mvc

trait RequestContextProvider {
  def currentRequest: RequestContext
}

class RequestContextProviderImpl extends RequestContextProvider {
  override def currentRequest = RequestOps.requestContext.value
}

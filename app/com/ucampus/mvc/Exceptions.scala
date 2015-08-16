package com.ucampus.mvc

final class EndpointNotImplemented extends RuntimeException("Endpoint not implemented")

final class ForbiddenRequest extends RuntimeException("403 Forbidden Request")

final class RequestParamInvalid(key: String) extends RuntimeException(s"Invalid value for [$key]")

object ResourceNotFound {
  def apply[T <: {val id: Int}](resource: T) = new ResourceNotFound(resource.getClass, resource.id)
  def apply[T](id: Int)(implicit m: Manifest[T]) = new ResourceNotFound(m.getClass, id)
}

class ResourceNotFound[T](clazz: Class[T], id: Int) extends RuntimeException(s"Resource of type ${clazz.getSimpleName} with id $id not found")

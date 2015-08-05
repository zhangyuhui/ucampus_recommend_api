package controllers

import com.wordnik.swagger.model.{ApiListing, ResourceListing}
import play.api.Logger
import play.api.libs.iteratee.Enumerator
import play.api.mvc.{RequestHeader, _}

import scala.collection.mutable

object SwaggerApis extends SwaggerBaseApiController {
  def getResources = Action {
    request =>
      implicit val requestHeader: RequestHeader = request

      val listing = getResourceListing
      val responseStr = (returnXml(request), listing.copy(apis = listing.apis.sortBy(_.path))) match {
        case (true, sortedListing) => toXmlString(sortedListing)
        case (false, sortedListing) => toJsonString(sortedListing)
      }
      returnValue(request, responseStr)
  }

  override def getResourceListing(implicit requestHeader: RequestHeader): ResourceListing = {
    super.getResourceListing
  }

  override def getApiListing(resourceName: String)(implicit requestHeader: RequestHeader): Option[ApiListing] = {
    super.getApiListing(resourceName)
  }

  def getResource(path: String) = Action {
    request =>
      implicit val requestHeader: RequestHeader = request

      val apiListing = getApiListing(path)

      val responseStr = returnXml(request) match {
        case true => toXmlString(apiListing)
        case false => toJsonString(apiListing)
      }
      Option(responseStr) match {
        case Some(help) => returnValue(request, help)
        case None =>
          val msg = new ErrorResponse(500, "api listing for path " + path + " not found")
          Logger("swagger").error(msg.message)
          if (returnXml(request)) {
            InternalServerError.chunked(Enumerator(toXmlString(msg).getBytes)).as("application/xml")
          } else {
            InternalServerError.chunked(Enumerator(toJsonString(msg).getBytes)).as("application/json")
          }
      }
  }
}

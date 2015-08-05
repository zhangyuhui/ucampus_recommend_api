import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext}
import ExecutionContext.Implicits.global

object LoggingFilter extends EssentialFilter {

  def apply(nextFilter: EssentialAction) = new EssentialAction {

    def apply(requestHeader: RequestHeader) = {
      val startTime = System.currentTimeMillis
      nextFilter(requestHeader).map {
        result =>
          val endTime = System.currentTimeMillis
          val responseTime = endTime - startTime
          requestHeader.tags.get("ROUTE_CONTROLLER") match {
            case Some(x) if x == "controllers.Assets" =>
            case _ =>
              Logger.info(s"[MOVIEDEMO_API] [Id:${System.currentTimeMillis() / 1000}-${requestHeader.id}] URI = ${requestHeader.uri} |  " +
                s"HTTPMethod = ${requestHeader.method} | ClientIP = ${requestHeader.remoteAddress} | " +
                s"ResponseTime = ${responseTime}ms | ResponseCode = ${result.header.status} | baseURL = ${requestHeader.uri.split("\\?").head}")
          }
          result.withHeaders("Response-Time" -> responseTime.toString)
      }
    }
  }
}

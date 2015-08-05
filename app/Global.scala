import com.typesafe.config.ConfigFactory
import com.google.inject.{Guice}
import com.moviedemo.mvc.{EndpointNotImplemented, ForbiddenRequest}
import com.moviedemo.settings.{Inject, InjectorContainer}
import java.io.File
import com.moviedemo.tools
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

object Global
  extends WithFilters(LoggingFilter) with GlobalSettings with Inject {

  private lazy val logger = {
    InjectorContainer.instance.getInstance(classOf[tools.AppLogger])
  }

  private val httpDateFormatter = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss z") //Thread-safe

  override def beforeStart(app: Application) {
    //System.setProperty( "sun.security.ssl.allowUnsafeRenegotiation", "true" );
    if (InjectorContainer.instance == null) {
      InjectorContainer.instance(Guice.createInjector(new ProductionModule, new ProductionModule))
    }
    super.beforeStart(app)
  }

  override def onStart(app: Application): Unit = {
    super.onStart(app)
  }

  override def getControllerInstance[A](clazz: Class[A]) = {
    InjectorContainer.instance.getInstance(clazz)
  }

  override def doFilter(action: EssentialAction): EssentialAction = super.doFilter {
    EssentialAction {
      request =>
        action.apply(request).map(_.withHeaders(responseHeaders(request): _*))
    }
  }

  override def onRequestReceived(request: RequestHeader): (RequestHeader, Handler) = {
    val list = List("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR")
    val clientIP: String = {
      getCustomHeader(request.headers, list) match {
        case Some(s) if(s.nonEmpty) => s
        case _ => request.remoteAddress
      }
    }
    val newRequest = request.copy(remoteAddress = clientIP)
    super.onRequestReceived(newRequest)
  }

  private def getCustomHeader(headers: Headers, list: List[String]): Option[String] = {
    list.collectFirst {
      case d if (headers.get(d).isDefined && !headers.get(d).get.equalsIgnoreCase("unknown")) => headers.get(d).get
    }
  }

  override def onError(requestHeader: RequestHeader, ex: Throwable): Future[Result] = {
    def isNotFound(ex: Throwable): Boolean = {
      ex match {
        case e: EndpointNotImplemented => true
        case null => false
        case _ => isNotFound(ex.getCause)
      }
    }

    def isForbidden(ex: Throwable): Boolean = {
      ex match {
        case e: ForbiddenRequest => true
        case null => false
        case _ => isForbidden(ex.getCause)
      }
    }

    ex match {
      case e if isNotFound(e) => Future.successful(Results.NotFound)
      case e if isForbidden(e) => Future.successful(Results.Forbidden)
      case e: PlayException => {
        Logger.info(s"[MOVIEDEMO_API] [Id:${System.currentTimeMillis() / 1000}-${requestHeader.id}] URI = ${requestHeader.uri} |  " +
            s"HTTPMethod = ${requestHeader.method} | ClientIP = ${requestHeader.remoteAddress} | exceptionId = ${e.id}")
        super.onError(requestHeader, ex).map(_.withHeaders(responseHeaders(requestHeader): _*))
      }
      case _ => {
        logger.error(ex.getMessage, ex)(requestHeader)
        super.onError(requestHeader, ex).map(_.withHeaders(responseHeaders(requestHeader): _*))
      }
    }
  }

  private def responseHeaders(request: RequestHeader): Seq[(String, String)] = {
    Seq(
      "Access-Control-Allow-Credentials" -> "true",
      "Access-Control-Allow-Origin" -> request.headers.get("Origin").getOrElse(""),
      "Access-Control-Allow-Methods" -> "GET, POST, PUT, DELETE, OPTIONS",
      "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept, Host, Api-Token, Authorization, X-Auth-Method, X-HTTP-Method-Override",
      "Expires" -> new DateTime(0).toString(httpDateFormatter)
    )
  }

  override def onLoadConfig(config: Configuration, path: File, classLoader: ClassLoader, mode: Mode.Mode): Configuration = {
    val modeSpecificConfig = config ++ Configuration(ConfigFactory.load(s"dsp.${mode.toString.toLowerCase}.conf"))
    super.onLoadConfig(modeSpecificConfig, path, classLoader, mode)
  }

  override def onRequestCompletion(request: RequestHeader) = {
    super.onRequestCompletion(request)
  }
}

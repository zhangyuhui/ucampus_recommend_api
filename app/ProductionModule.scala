import com.ucampus.data.repositories.CourseRepositoryImpl
import com.ucampus.domain.interfaces._
import com.ucampus.mvc.Formats
import com.ucampus.mvc._
import com.tzavellas.sse.guice.ScalaModule
import com.ucampus.data.models.ModelFormats
import com.ucampus.services._
import com.ucampus.data.repositories._
import com.ucampus.tools._

class ProductionModule extends ScalaModule {
  def configure() {

    // Formats
    Formats.registerWrites(ModelFormats.writesMap)
    Formats.registerRecordTypes(ModelFormats.recordTypes)

    // Service
    bind[CourseService].to[CourseServiceImpl]

    // Repository
    bind[CourseRepository].to[CourseRepositoryImpl]

    // Other
    bind[Cache].to[CacheImpl]
    bind[AppLogger].to[LoggerImpl]
    bind[ResourceCache].to[ResourceCacheImpl]
  }
}



import com.moviedemo.domain.interfaces._
import com.moviedemo.mvc.Formats
import com.moviedemo.mvc._
import com.tzavellas.sse.guice.ScalaModule
import com.moviedemo.data.models.ModelFormats
import com.moviedemo.services._
import com.moviedemo.data.repositories._
import com.moviedemo.tools._

class ProductionModule extends ScalaModule {
  def configure() {

    // Formats
    Formats.registerWrites(ModelFormats.writesMap)
    Formats.registerRecordTypes(ModelFormats.recordTypes)

    // Service
    bind[MovieService].to[MovieServiceImpl]
    bind[UserService].to[UserServiceImpl]
    bind[ScoreService].to[ScoreServiceImpl]

    // Repository
    bind[MovieRepository].to[MovieRepositoryImpl]
    bind[UserRepository].to[UserRepositoryImpl]
    bind[ScoreRepository].to[ScoreRepositoryImpl]

    // Other
    bind[Cache].to[CacheImpl]
    bind[AppLogger].to[LoggerImpl]
    bind[ResourceCache].to[ResourceCacheImpl]
  }
}



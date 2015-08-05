package controllers

import com.google.inject.Inject
import com.moviedemo.data.models.ModelFormats._
import com.moviedemo.data.models.{Rating}
import com.moviedemo.domain.interfaces.ScoreService
import com.moviedemo.mvc.{RequestHelper, ApiController}
import com.moviedemo.mvc.RequestOps._
import com.wordnik.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}
import play.api.mvc.{AnyContent, Request}

@Api(value = "/scores", description = "Operations for Scores.")
class Scores @Inject()(val service: ScoreService) extends ApiController.T1[Rating](service) {

  @ApiOperation(value = "Retrieve scores (searching, paging, sorting)",
    notes = "The returned type is a list of ratings.",
    response = classOf[Rating],
    httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "Int", paramType = "query"),
    new ApiImplicitParam(name = "limit", value = "Page size", required = false, dataType = "Int", paramType = "query"),
    new ApiImplicitParam(name = "sort", value = "Column to sort by", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "dir", value = "Sort direction", allowableValues = "asc,desc", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "user_id", value = "User id", required = false, dataType = "Int", paramType = "query"),
    new ApiImplicitParam(name = "movie_id", value = "Movie id", required = false, dataType = "Int", paramType = "query")
  ))
  override def index = authenticate {
    implicit request: Request[AnyContent] => {
      val userId = RequestHelper.getParamAsInt("user_id")
      val movieId = RequestHelper.getParamAsInt("movie_id")
      ApiController.index[Rating](service.index(userId, movieId)(searchOptions))
    }
  }
}


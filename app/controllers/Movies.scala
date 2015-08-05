package controllers

import com.google.inject.Inject
import com.moviedemo.macros.ValidationMacros.className
import com.moviedemo.data.models.ModelFormats._
import com.moviedemo.data.models.Movie
import com.moviedemo.domain.interfaces.MovieService
import com.moviedemo.mvc.{ApiController}
import com.wordnik.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}
import play.api.libs.json._
import play.api.mvc._

@Api(value = "/movies", description = "Operations for Movies.")
class Movies @Inject()(val service: MovieService) extends ApiController.T1[Movie](service) {

  @ApiOperation(value = "Retrieve movies (searching, paging, sorting)",
    notes = "The returned type is a list of movies.",
    response = classOf[Movie],
    httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "Int", paramType = "query"),
    new ApiImplicitParam(name = "limit", value = "Page size", required = false, dataType = "Int", paramType = "query"),
    new ApiImplicitParam(name = "sort", value = "Column to sort by", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "dir", value = "Sort direction", allowableValues = "asc,desc", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "query", value = "Search term", required = false, dataType = "String", paramType = "query")
  ))
  override def index = super.index
}


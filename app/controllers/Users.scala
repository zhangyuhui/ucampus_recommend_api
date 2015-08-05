package controllers

import com.google.inject.Inject
import com.moviedemo.data.models.ModelFormats._
import com.moviedemo.data.models.User
import com.moviedemo.domain.interfaces.UserService
import com.moviedemo.mvc.ApiController
import com.wordnik.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}

@Api(value = "/users", description = "Operations for Users.")
class Users @Inject()(val service: UserService) extends ApiController.T1[User](service) {

  @ApiOperation(value = "Retrieve users (searching, paging, sorting)",
    notes = "The returned type is a list of users.",
    response = classOf[User],
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


package controllers

import com.google.inject.Inject
import com.ucampus.data.models.ModelFormats._
import com.ucampus.data.models.Course
import com.ucampus.tools.StringOp._
import com.ucampus.tools.Conversions._
import com.ucampus.domain.interfaces.CourseService
import com.ucampus.mvc.{RequestHelper, ApiController}
import com.ucampus.mvc.RequestOps._
import com.wordnik.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}
import play.api.mvc.{AnyContent, Request}

@Api(value = "/courses", description = "Operations for Courses.")
class Courses @Inject()(val service: CourseService) extends ApiController.T1[Course](service) {

  @ApiOperation(value = "Retrieve recommended courses",
    notes = "The returned type is a list of users.",
    response = classOf[Course],
    httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "major1", value = "一级学科名字", required = true, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "major2", value = "二级学科名字", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "major3", value = "以前的学科名字 (可能只对硕博有效)", required = false, dataType = "String", paramType = "query"),
    new ApiImplicitParam(name = "department", value = "所在系名字", required = false, dataType = "String", paramType = "query")
  ))
  override def index = authenticate {
    implicit request: Request[AnyContent] => {
      def getParamAsString(key: String): Option[String] = {
        val value = RequestHelper.getParamAsString(key)
        if (value.isNullOrEmpty) None else value
      }
      val major1 = getParamAsString("major1")
      val major2 = getParamAsString("major2")
      val major3 = getParamAsString("major3")
      val department = getParamAsString("department")
      ApiController.index[Course](service.indexByRecommend(major1.get, major2, major3, department))
    }
  }
}
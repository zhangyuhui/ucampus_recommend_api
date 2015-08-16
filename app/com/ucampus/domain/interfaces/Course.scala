package com.ucampus.domain.interfaces

import com.ucampus.data.models.Course
import com.ucampus.mvc.{ServiceResult, RepositoryBase, ServiceBase}

trait CourseRepository extends RepositoryBase.T1[Course] {

}

trait CourseService extends ServiceBase.T1[Course] {
  def indexByRecommend(major1: String, major2: Option[String], major3: Option[String], department: Option[String]): ServiceResult[List[Course]]
}

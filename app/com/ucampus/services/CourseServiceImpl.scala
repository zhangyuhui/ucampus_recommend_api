package com.ucampus.services

import com.google.inject.Inject
import com.ucampus.data.models.Course
import com.ucampus.domain.interfaces._
import com.ucampus.mvc.ServiceResult
import com.ucampus.tools.Conversions._
import com.ucampus.recommend.RecEsV05Pub

class CourseServiceImpl @Inject()(implicit val repository: CourseRepository) extends CourseService {
  override def indexByRecommend(major1: String, major2: Option[String], major3: Option[String], department: Option[String]): ServiceResult[List[Course]] = {
    val recommend =  RecEsV05Pub.CourseRelevance(major1, major2.getOrElse(""), department.getOrElse(""), department.getOrElse(""))
    //TODO
    //Check the courses responded form recommend service
    //And covert them into the course list here
    Nil
  }
}


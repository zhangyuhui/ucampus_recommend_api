package com.ucampus.services

import java.util

import com.google.inject.Inject
import com.ucampus.data.models.Course
import com.ucampus.domain.interfaces._
import com.ucampus.mvc.ServiceResult
import com.ucampus.tools.Conversions._
import com.ucampus.recommend.RecEsV05Pub
import scala.collection.mutable.ListBuffer

class CourseServiceImpl @Inject()(implicit val repository: CourseRepository) extends CourseService {
  override def indexByRecommend(major1: String, major2: Option[String], major3: Option[String], department: Option[String]): ServiceResult[List[Course]] = {
    val recommend =  RecEsV05Pub.CourseRelevance(major1, major2.getOrElse(""), department.getOrElse(""), department.getOrElse(""))

    val courses = new ListBuffer[Course]()
    val iterator = recommend.iterator

    var index = 0
    while(iterator.hasNext() ) {
      val map = iterator.next()
      val key = map.keySet().toArray()(0)
      val value = map.get(key)
      val source = value.get("source")
      val data = source.asInstanceOf[util.HashMap[String, AnyRef]]
      def getProperty(prop: String) = {
        val value = data.get(prop)
        if (value == null) ""
        else value.toString
      }
      val courseName = getProperty("courseName")
      val courseDesc = getProperty("courseDesc")
      val teacherName = getProperty("teacherName")
      val teacherDesc = getProperty("teacherDesc")
      val university = getProperty("university")
      val category = getProperty("category")
      val url = getProperty("url")

      index += 1

      courses += Course(index, key.toString, courseName, courseDesc, teacherName, teacherDesc, university, category, url)
    }
    courses.toList
  }
}


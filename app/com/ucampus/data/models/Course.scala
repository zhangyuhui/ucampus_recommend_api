package com.ucampus.data.models

import javax.xml.bind.annotation.XmlRootElement

import scala.beans.BeanProperty
import com.wordnik.swagger.annotations.ApiModelProperty
import scala.annotation.meta.field

@XmlRootElement(name = "Course")
case class Course(
    @(ApiModelProperty@field)(value = "Course ID")
    @BeanProperty
    id: Int,

    @(ApiModelProperty@field)(value = "Course Name")
    @BeanProperty
    name: String) extends HasId[Int]
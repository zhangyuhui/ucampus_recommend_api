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

    @(ApiModelProperty@field)(value = "Course Key")
    @BeanProperty
    key: String,

    @(ApiModelProperty@field)(value = "Course Name")
    @BeanProperty
    name: String,

    @(ApiModelProperty@field)(value = "Course Description")
    @BeanProperty
    description: String,

    @(ApiModelProperty@field)(value = "Course Teacher Name")
    @BeanProperty
    teacherName: String,

    @(ApiModelProperty@field)(value = "Course Teacher Description")
    @BeanProperty
    teacherDescription: String,

    @(ApiModelProperty@field)(value = "Course University")
    @BeanProperty
    university: String,

    @(ApiModelProperty@field)(value = "Course Category")
    @BeanProperty
    category: String,

    @(ApiModelProperty@field)(value = "Course Url")
    @BeanProperty
    url: String) extends HasId[Int]


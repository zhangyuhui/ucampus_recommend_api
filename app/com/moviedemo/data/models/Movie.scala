package com.moviedemo.data.models

import javax.xml.bind.annotation.XmlRootElement

import scala.beans.BeanProperty
import com.wordnik.swagger.annotations.ApiModelProperty
import scala.annotation.meta.field

@XmlRootElement(name = "Movie")
case class Movie(
    @(ApiModelProperty@field)(value = "Movie ID")
    @BeanProperty
    id: Int,

    @(ApiModelProperty@field)(value = "Name")
    @BeanProperty
    name: String,

    @(ApiModelProperty@field)(value = "Year")
    @BeanProperty
    year: String) extends HasId[Int]
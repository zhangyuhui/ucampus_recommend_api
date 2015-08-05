package com.moviedemo.data.models

import javax.xml.bind.annotation.XmlRootElement

import com.wordnik.swagger.annotations.ApiModelProperty

import scala.annotation.meta.field
import scala.beans.BeanProperty

@XmlRootElement(name = "User")
case class User(
    @(ApiModelProperty@field)(value = "User ID")
    @BeanProperty
    id: Int,

    @(ApiModelProperty@field)(value = "Name")
    @BeanProperty
    name: String) extends HasId[Int]
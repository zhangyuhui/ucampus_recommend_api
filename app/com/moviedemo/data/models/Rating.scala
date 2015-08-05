package com.moviedemo.data.models

import javax.xml.bind.annotation.XmlRootElement

import com.wordnik.swagger.annotations.ApiModelProperty

import scala.annotation.meta.field
import scala.beans.BeanProperty

@XmlRootElement(name = "Rating")
case class Rating(
    @(ApiModelProperty@field)(value = "Rating ID")
    @BeanProperty
    id: Int,

    @(ApiModelProperty@field)(value = "User Id")
    @BeanProperty
    userId: Int,

    @(ApiModelProperty@field)(value = "User Name")
    @BeanProperty
    userName: String,

    @(ApiModelProperty@field)(value = "Movie Id")
    @BeanProperty
    movieId: Int,

    @(ApiModelProperty@field)(value = "Movie Name")
    @BeanProperty
    movieName: String,

    @(ApiModelProperty@field)(value = "True Rating")
    @BeanProperty
    trueRating: Double,

    @(ApiModelProperty@field)(value = "MF Rating")
    @BeanProperty
    mfRating: Double,

    @(ApiModelProperty@field)(value = "Naive Rating")
    @BeanProperty
    naiveRating: Double ) extends HasId[Int]
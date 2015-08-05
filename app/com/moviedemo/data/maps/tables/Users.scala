package com.moviedemo.data.maps.tables

import com.moviedemo.data.maps.{TableMapWithIdentity}
import com.moviedemo.data.models.User

import scala.slick.direct.AnnotationMapper.column

object Users extends TableMapWithIdentity[User]("movie", "user") {

  override def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)

  override def name = column[String]("name")

  def * = id ~ name <> (User, User.unapply _)
}
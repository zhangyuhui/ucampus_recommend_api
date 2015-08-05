package com.moviedemo.data.maps.tables

import com.moviedemo.data.maps.{TableMapWithIdentity}
import com.moviedemo.data.models.Movie

object Movies extends TableMapWithIdentity[Movie]("movie", "movie") {

  override def id = column[Int]("movie_id", O.PrimaryKey, O.AutoInc)

  override def name = column[String]("name")

  def year = column[String]("year")

  def * = id ~ name ~ year <> (Movie, Movie.unapply _)
}
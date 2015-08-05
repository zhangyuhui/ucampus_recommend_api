package com.moviedemo.data.maps.tables

import com.moviedemo.data.maps.{TableMapWithIdentity}
import com.moviedemo.data.maps.TableConversion._
import com.moviedemo.data.maps.TypeMappers
import TypeMappers._
import com.moviedemo.data.models.Rating

abstract class ScoresTable(tableName: String) extends TableMapWithIdentity[Rating]("movie", tableName) {

  override def id = column[Int]("rating_id", O.PrimaryKey, O.AutoInc)

  def userId = column[Int]("user_id")

  def userName = column[String]("user_name")

  def movieId = column[Int]("movie_id")

  def movieName = column[String]("movie_name")

  def trueRating = column[Double]("true_rating")

  def mfRating = column[Double]("mf_rating")

  def naiveRating = column[Double]("naive_rating")

  def * = id ~ userId~ userName ~ movieId ~ movieName ~ trueRating ~ mfRating ~ naiveRating <> (Rating, (Rating.unapply _))
}


object ScoresAll extends ScoresTable("score_all_view") {
}

object ScoresAllSelect extends ScoresTable("score_all_select_view") {
}

object ScoresOut extends ScoresTable("score_out_view") {
}

object ScoresOutSelect extends ScoresTable("score_out_select_view") {
}
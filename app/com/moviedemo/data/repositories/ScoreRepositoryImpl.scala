package com.moviedemo.data.repositories

import com.moviedemo.data.maps.tables.{Users, Movies, ScoresAll}
import com.moviedemo.data.models.{Rating}
import com.moviedemo.domain.interfaces.ScoreRepository
import com.moviedemo.mvc.SearchOptions
import com.moviedemo.tools.Conversions._
import com.moviedemo.tools.searchImplicits._
import play.api.db.slick.Config.driver.simple._

import scala.slick.session.Session

class ScoreRepositoryImpl extends ScoreRepository {
  override def table = ScoresAll

  override def index(searchOptions: SearchOptions)(implicit session: Session): List[Rating] = {
    implicit val options = searchOptions
    Query(ScoresAll).withEntitySearch.getResults(ScoresAll)
  }

  def index(userId: Option[Int], movieId: Option[Int])(implicit session: Session, searchOptions: SearchOptions): List[Rating] = {
    val query = {
      (userId, movieId) match {
        case (Some(u), Some(m)) => Query(ScoresAll).filter(p => p.userId === u && p.movieId === movieId)
        case (Some(u), None) => Query(ScoresAll).filter(_.userId === u)
        case (None, Some(m)) => Query(ScoresAll).filter(_.movieId === m)
        case _ => Query(ScoresAll)
      }
    }
    query.withEntitySearch.getResults(ScoresAll)
  }
}

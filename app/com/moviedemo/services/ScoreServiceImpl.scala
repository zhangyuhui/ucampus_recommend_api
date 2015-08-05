package com.moviedemo.services

import com.google.inject.Inject
import com.moviedemo.data.models.Rating
import com.moviedemo.domain.interfaces._
import com.moviedemo.tools.Conversions._
import com.moviedemo.mvc.{ServiceResult, SearchOptions}
import com.moviedemo.settings.ApplicationSettings.Databases._

import scala.slick.session.Session

class ScoreServiceImpl @Inject()(implicit val repository: ScoreRepository) extends ScoreService {
  override  def index(userId: Option[Int], movieId: Option[Int])(implicit searchOptions: SearchOptions): ServiceResult[List[Rating]] = {
    DB.withSession {
      implicit session: Session => repository.index(userId, movieId)
    }
  }
}

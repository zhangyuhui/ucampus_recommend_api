package com.moviedemo.domain.interfaces

import com.moviedemo.data.models.{Rating}
import com.moviedemo.mvc.{SearchOptions, ServiceResult, RepositoryBase, ServiceBase}

import scala.slick.session.Session

trait ScoreRepository extends RepositoryBase.T1[Rating] {
  def index(userId: Option[Int], movieId: Option[Int])(implicit session: Session, searchOptions: SearchOptions): List[Rating]
}

trait ScoreService extends ServiceBase.T1[Rating] {
  def index(userId: Option[Int], movieId: Option[Int])(implicit searchOptions: SearchOptions): ServiceResult[List[Rating]]
}

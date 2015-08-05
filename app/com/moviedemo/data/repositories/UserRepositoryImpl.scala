package com.moviedemo.data.repositories

import com.moviedemo.data.maps.tables.Users
import com.moviedemo.data.models.User
import com.moviedemo.domain.interfaces.UserRepository
import com.moviedemo.mvc.SearchOptions
import com.moviedemo.tools.Conversions._
import com.moviedemo.tools.searchImplicits._
import play.api.db.slick.Config.driver.simple._

import scala.slick.session.Session

class UserRepositoryImpl extends UserRepository {
  override def table = Users

  override def index(searchOptions: SearchOptions)(implicit session: Session): List[User] = {
    implicit val options = searchOptions
    Query(Users).withEntitySearch.getResults(Users)
  }
}

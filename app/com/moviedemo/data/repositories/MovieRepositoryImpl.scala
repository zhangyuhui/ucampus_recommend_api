package com.moviedemo.data.repositories

import com.moviedemo.data.maps.tables.Movies
import com.moviedemo.data.models.Movie
import com.moviedemo.domain.interfaces.MovieRepository
import com.moviedemo.mvc.SearchOptions
import com.moviedemo.tools.Conversions._
import com.moviedemo.tools.searchImplicits._
import play.api.db.slick.Config.driver.simple._
import scala.slick.session.Session

class MovieRepositoryImpl extends MovieRepository {
  override def table = Movies

  override def index(searchOptions: SearchOptions)(implicit session: Session): List[Movie] = {
    implicit val options = searchOptions
    Query(Movies).withEntitySearch.getResults(Movies)
  }
}

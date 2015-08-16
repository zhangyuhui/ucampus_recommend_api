package com.ucampus.mvc

import com.google.inject.Inject
import com.ucampus.data.models.HasId
import scala.slick.session.Session
import com.ucampus.settings.ApplicationSettings.Databases._
import com.ucampus.tools.Conversions._

trait ServiceBase[T <: HasId[Int], D <: HasId[Int]] {
  val repository: RepositoryBase[T, D]

  def create(entity: D): ServiceResult[Int] = {
    validate(entity) {
      DB.withSession {
        implicit session: Session =>
          val id = repository.create(entity)
          ServiceResult(id)
      }
    }
  }

  def update(entity: D): ServiceResult[Int] = {
    validate(entity) {
      DB.withSession {
        implicit session: Session =>
          repository.update(entity)
          ServiceResult(entity.id)
      }
    }
  }

  def remove(id: Int) {
    DB.withSession {
      implicit session: Session =>
        repository.remove(id)
    }
  }

  def edit(id: Int): ServiceResult[Option[D]] = {
    DB.withSession {
      implicit session: Session =>
        repository.get(id)
    }
  }

  def show(id: Int): ServiceResult[Option[T]] = {
    DB.withSession {
      implicit session: Session =>
        repository.show(id)
    }
  }

  def exists(id: Int): Boolean = {
    DB.withSession {
      implicit session: Session =>
        repository.exists(id)
    }
  }

  def index(searchOptions: SearchOptions): ServiceResult[List[T]] = {
    DB.withSession {
      implicit session: Session =>
        repository.index(searchOptions)
    }
  }

  def validate(entity: D)(ifValid: => ServiceResult[Int]): ServiceResult[Int] = {
    //val messages = ValidationHelper.validate(entity)
    val messages = Nil

    if (messages.isEmpty) ifValid
    else ServiceResult(entity.id, messages = messages)
  }

  def validateAll(entities: List[D])(ifValid: => ServiceResult[List[Int]]): ServiceResult[List[Int]] = {
    //val messages = ValidationHelper.validate(entities)
    val messages = Nil

    if (messages.isEmpty) ifValid
    else ServiceResult(entities.map(_.id), messages = messages)
  }
}

class ServiceBaseImpl[T <: HasId[Int], D <: HasId[Int]] @Inject()
(implicit val repository: RepositoryBase[T, D]) extends ServiceBase[T, D] {

}

object ServiceBase {
  type T1[T <: HasId[Int]] = ServiceBase[T, T]
}

object ServiceBaseImpl {
  type T1[T <: HasId[Int]] = ServiceBaseImpl[T, T]
}

case class SearchOptions(query: String, sortColumn: String, sortDirection: String, pageIndex: Option[Int], pageSize: Option[Int]) {
  def withDefaultSort(sortColumn: String, sortDirection: Option[String] = None): SearchOptions = {
    SearchOptions(
      this.query,
      Option(this.sortColumn).getOrElse(sortColumn),
      if (Option(this.sortColumn).isDefined) this.sortDirection else Option(this.sortDirection).getOrElse(sortDirection.get),
      this.pageIndex,
      this.pageSize
    )
  }
}

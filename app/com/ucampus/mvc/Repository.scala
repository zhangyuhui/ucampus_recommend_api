package com.ucampus.mvc

import com.ucampus.data.maps.EntityColumnMapping
import com.ucampus.data.models.HasId
import scala.slick.session.Session
import scala.slick.lifted.Query
import play.api.db.slick.Config.driver.simple._

object Restful {
  type T1[T] = Restful[T, T]
}

trait Restful[T, D] extends Index[T] {
  def create(entity: D)(implicit session: Session): Int

  def createAll(entities: D*)(implicit session: Session): Seq[Int]

  def update(entity: D)(implicit session: Session)

  def remove(id: Int)(implicit session: Session)

  def get(id: Int)(implicit session: Session): Option[D]

  def show(id: Int)(implicit session: Session): Option[T]
}

trait Index[T] {
  def index(searchOptions: SearchOptions)(implicit session: Session): List[T]
}

trait RepositoryBase[T <: HasId[Int], D <: HasId[Int]] extends Restful[T, D] with TableOperations[T, D] {

  def table: Option[Table[_] with EntityColumnMapping] = None

  override def update(entity: D)(implicit session: Session): Unit = {
    table match {
      case Some(t: Table[D] with EntityColumnMapping) => Query(t).filter(_.id === entity.id).update(entity)
      case _ => super.update(entity)
    }
  }

  override def get(id: Int)(implicit session: Session): Option[D] = {
    table match {
      case Some(t: Table[D] with EntityColumnMapping) => Query(t).filter(_.id === id).firstOption
      case _ => super.get(id)
    }
  }

  override def create(entity: D)(implicit session: Session): Int = {
    table match {
      case Some(t: Table[D] with EntityColumnMapping) => t.returning(t.id).insert(entity)
      case _ => super.create(entity)
    }
  }

  override def createAll(entities: D*)(implicit session: Session): Seq[Int] = {
    table match {
      case Some(t: Table[D] with EntityColumnMapping) => t.returning(t.id).insertAll(entities: _*).toList
      case _ => super.createAll(entities: _*)
    }
  }

  override def remove(id: Int)(implicit session: Session): Unit = {
    table match {
      case Some(t: Table[_] with EntityColumnMapping) => Query(t).filter(_.id === id).delete
      case _ => super.remove(id)
    }
  }

  def exists(id: Int)(implicit session: Session): Boolean = {
    table match {
      case Some(t: Table[_] with EntityColumnMapping) => Query(Query(t).filter(_.id === id).exists).first
      case _ => throw new EndpointNotImplemented
    }
  }
}

object RepositoryBase {
  type T1[T <: HasId[Int]] = RepositoryBase[T, T]
}


trait TableOperations[T, D] extends Restful[T, D] {

  def create(entity: D)(implicit session: Session): Int = {
    throw new EndpointNotImplemented
  }

  def createAll(entities: D*)(implicit session: Session): Seq[Int] = {
    throw new EndpointNotImplemented
  }

  def update(entity: D)(implicit session: Session) {
    throw new EndpointNotImplemented
  }

  def remove(id: Int)(implicit session: Session) {
    throw new EndpointNotImplemented
  }

  def get(id: Int)(implicit session: Session): Option[D] = {
    throw new EndpointNotImplemented
  }

  def show(id: Int)(implicit session: Session): Option[T] = {
    throw new EndpointNotImplemented
  }

  def index(searchOptions: SearchOptions)(implicit session: Session): List[T] = {
    throw new EndpointNotImplemented
  }
}

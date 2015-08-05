package com.moviedemo.data.maps

import java.lang.reflect.Method
import java.sql.Timestamp

import com.moviedemo.tools.Conversions._
import org.joda.time.DateTime
import play.api.db.slick.Config.driver.simple._

import scala.collection.mutable.{Map => MMap}
import scala.slick.ast.{FieldSymbol, IntrinsicSymbol, Node, Ref, Select}
import scala.slick.jdbc.GetResult
import scala.slick.lifted
import scala.slick.lifted.{Column, MappedTypeMapper, _}

object TableMapWithIdentity {
  val columnClassMap: MMap[Class[_], Map[String, Method]] = MMap.empty
}

abstract class TableMapWithIdentity[T](schemaName: Option[String], tableName: String) extends TableMap[T](schemaName, tableName) with EntityColumnMapping {

  def this(tableName: String) = this(None, tableName)

  def this(schemaName: String, tableName: String) = this(Some(schemaName), tableName)

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def idForInsert = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")
}

abstract class TableMap[T](schemaName: Option[String], tableName: String) extends Table[T](schemaName, tableName) with ColumnMapping {
  table =>

  def this(tableName: String) = this(None, tableName)

  override def column[C](n: String, options: ColumnOption[C]*)(implicit tm: lifted.TypeMapper[C]): lifted.Column[C] = {
    val result = super.column(n, options: _*)(tm)
    // columnMap(n.toLowerCase) = result
    result
  }

  def columnMap: Map[String, Column[_]] = {
    import TableMapWithIdentity.columnClassMap
    val methods = columnClassMap.getOrElse(getClass, {
      val map = getColumnMap
      columnClassMap += getClass -> map
      map
    })

    methods.map {
      case (name, method) => {
        name -> method.invoke(this).asInstanceOf[Column[_]]
      }
    }.toMap
  }

  def allNode: Option[Node] = None

  // And override create_* to get the DDL for all columns.
  // Yeah, this is ugly. It used to be much simpler in ScalaQuery.
  // We can add a helper method to simplify it.
  override def create_* = {
    allNode match {
      case Some(q) => {
        allNode.collect {
          case Select(Ref(IntrinsicSymbol(in)), f: FieldSymbol) if in == this => f
        }.toSeq.distinct
      }
      case _ => super.create_*
    }
  }

  override def instance: ColumnMapping = super.instance

  protected def getColumnMap: Map[String, Method] = {
    (for {m <- getClass.getMethods.view} yield {
      if (m.getReturnType == classOf[Column[_]] &&
        m.getParameterTypes.length == 0) {
        Some(m.getName.toLowerCase -> m)
      } else None
    }).flatten.toMap
  }

  def fromTuple[T <: Product](x: T): Map[String, Column[_]] = {
    val list = x.productIterator.toList
    var result: Map[String, Column[_]] = Map.empty
    val map = columnMap.toList
    var counter = 0
    if (list.length != map.length) throw ScalaReflectionException("The provided tuple elements and the column map of the table/view mismatch. " +
      "Make sure you return all the properties in the tuple that specified table up implements.")

    for (m <- list) {
      result += map(counter)._1 -> m.asInstanceOf[Column[_]]
      counter += 1
    }

    result
  }

  def empty[A: lifted.TypeMapper] = lifted.ConstColumn(Option.empty[A])
}

object TypeMappers {

  private lazy val oneBillion = 1000000000D

  implicit def date2dateTime = MappedTypeMapper.base[DateTime, Timestamp](
    dateTime => new Timestamp(dateTime.getMillis),
    date => new DateTime(date)
  )

  def emptyStringToNull = MappedTypeMapper.base[Option[String], String](
    s => s match {
      case None => ""
      case Some(x) => x
    },
    s => s match {
      case "" => None
      case x => Some(x)
    }
  )

  def budgetOneBillionToNull = MappedTypeMapper.base[Option[Double], Option[Double]](
  {
    b => b match {
      case Some(x) => Option(x)
      case None => Option(oneBillion)
    }
  }, {

    i => i match {
      case Some(db) if db != oneBillion => Some(i)
      case _ => None
    }
  })

  def daysToSeconds = MappedTypeMapper.base[Int, Int](
    days => days * 24 * 60 * 60,
    seconds => if (seconds == 0) 0 else seconds / (24 * 60 * 60)
  )

  def minusOneToNullInt = MappedTypeMapper.base[Option[Int], Option[Int]](
    n => n match {
      case None => Some(-1)
      case x => x
    },
    n => n match {
      case Some(-1) => None
      case x => x
    }
  )

  def minusOneToNullDouble = MappedTypeMapper.base[Option[Double], Option[Double]](
    n => n match {
      case None => Some(-1d)
      case x => x
    },
    n => n match {
      case Some(-1d) => None
      case x => x
    }
  )

  implicit val getNextDate = GetResult(t => t.nextDate())

  // String -> Seq[Int]
  implicit def idsToSeq(ids: String): Seq[Int] = if (ids != null) ids.split("\\|").map(_.toInt) else Nil

  implicit def idsToSeqOption(ids: String): Option[Seq[Int]] = if (ids != null) Some(ids.split("\\|").map(_.toInt)) else None

  // String -> Seq[String]
  implicit def keysToSeq(ids: String): Seq[String] = if (ids != null) ids.split("\\|") else Nil

  implicit def keysToSeqOption(ids: String): Option[Seq[String]] = if (ids != null) Some(ids.split("\\|")) else None
}
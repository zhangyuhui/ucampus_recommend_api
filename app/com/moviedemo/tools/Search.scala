package com.moviedemo.tools

import com.moviedemo.data.maps.{ColumnMapping, EntityColumnMapping, TableMapWithIdentity}
import com.moviedemo.mvc.SearchOptions
import com.moviedemo.tools.DatabaseOp._
import com.moviedemo.settings.ApplicationSettings.Databases._
import com.moviedemo.tools.StringOp._
import play.api.db.slick.Config.driver.simple._

import scala.reflect.runtime.universe._
import scala.slick.lifted.{Column, Query}
import scala.slick.session.Session

object MatchType extends Enumeration {
  type SearchConditionMatchType = Value
  val Exact, Like = Value
}

case class SearchCondition(columnName: String, value: String, matchType: MatchType.Value)

case class PagedQuery[T, U](total: Query[T, U], paged: Query[T, U])

class SearchHelper[T <: TableMapWithIdentity[U], U](q: Query[T, U]) {
  def withEntitySearch()(implicit searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => x.id === ParseOp.parse[Int](searchOptions.query) || x.name.contains(searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => x.name.contains(searchOptions.query))
    else q
  }

  def getResults(table: ColumnMapping)(implicit session: Session, searchOptions: SearchOptions) = {
    if (searchOptions == null) q.list
    else new SearchHelperSelector(q).withSortingAndPaging(table, t => t).paged.list
  }
}

class SearchHelperSelector[T, U](q: Query[T, U]) {
  def withEntitySearchSelector(selector: T => EntityColumnMapping)(implicit searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => selector(x).id === ParseOp.parse[Int](searchOptions.query) || selector(x).name.contains(searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => selector(x).name.contains(searchOptions.query))
    else q
  }

  def getResultsSelector(table: ColumnMapping, selector: T => ColumnMapping, default: T => Column[_] = null, defaultDirection: String = "asc")(implicit session: Session, searchOptions: SearchOptions) = {
    withSortingAndPaging(table, selector, default, defaultDirection).paged.list
  }

  def withSorting(table: ColumnMapping, selector: T => ColumnMapping, default: T => Column[_] = null, defaultDirection: String = "asc")(implicit searchOptions: SearchOptions): Query[T, U] = {

    if (searchOptions != null && table.columnMap.contains(searchOptions.sortColumn)) {
      import searchOptions._
      if (sortDirection == "desc") q.sortBy(selector(_).columnMap(sortColumn).desc)
      else q.sortBy(selector(_).columnMap(sortColumn).asc)
    } else if (default != null) {
      q.sortBy(x => {
        val column = default(x)
        if (defaultDirection == "desc") column.desc else column.asc
      })
    }
    else {
      q
    }
  }

  def withPaging()(implicit searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    import searchOptions._
    if (pageSize.isDefined && pageIndex.isDefined) {
      if (pageIndex.get > 1) q.drop((pageIndex.get - 1) * pageSize.get).take(pageSize.get)
      else q.take(pageSize.get)
    } else q
  }

  def withSortingAndPaging(table: ColumnMapping, selector: T => ColumnMapping, default: T => Column[_] = null, defaultDirection: String = "asc")(implicit searchOptions: SearchOptions) = {
    val total = withSorting(table.instance, selector, default, defaultDirection)
    PagedQuery(total, new SearchHelperSelector(total).withPaging)
  }
}

class SearchHelperTuple[T <: Product, U](q: Query[T, U]) {
  def withEntitySearchTuple(idNameSelector: T => (Column[Int], Column[String]))(implicit searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => idNameSelector(x)._1 === ParseOp.parse[Int](searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => idNameSelector(x)._2.contains(searchOptions.query))
    else q
  }

  def withSortingTuple(selector: T => Map[String, Column[_]], default: T => Column[_], defaultDirection: String = "asc")(implicit searchOptions: SearchOptions) = {
    if (default == null) {
      q
    } else {
      val sel = searchImplicits.toKeyLowerCase(selector)
      import searchOptions._
      q.sortBy {
        x =>
        //Apply specified sort direction to default column if no sort column specified
          val defaultSortDir = if (sortColumn.isNullOrEmpty && !sortDirection.isNullOrEmpty) sortDirection else defaultDirection
          val defaultSort = if (defaultSortDir == "desc") default(x).desc else default(x).asc
          //Sort by specified column, then by default column
          sel(x).get(sortColumn).map {
            y =>
              (if (sortDirection == "desc") y.desc else y.asc, defaultSort): scala.slick.lifted.Ordered
          }.headOption.getOrElse(defaultSort)
      }
    }
  }

  def withPaging()(implicit searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    import searchOptions._
    if (pageSize.isDefined && pageIndex.isDefined) {
      if (pageIndex.get > 1) q.drop((pageIndex.get - 1) * pageSize.get).take(pageSize.get)
      else q.take(pageSize.get)
    } else q
  }

  def withSortingAndPagingTuple(selector: T => Map[String, Column[_]], default: T => Column[_] = null)(implicit searchOptions: SearchOptions) = {
    //TODO(bryan) Is a default sorting parameter needed here? This can be provided in SearchOptions.
    val total = withSortingTuple(selector, default)
    val paged: Query[T, U] = new SearchHelperSelector(total).withPaging
    PagedQuery(total, paged)
  }

  def getResultsTuple(selector: T => Map[String, Column[_]] = Map.empty, default: T => Column[_] = null)(implicit session: Session, searchOptions: SearchOptions = null) = {
    if (searchOptions == null) q.list
    else withSortingAndPagingTuple(selector, default).paged.list
  }
}


object searchImplicits {

  def toKeyLowerCase[T](selector: T => Map[String, Column[_]]): T => Map[String, Column[_]] = {
    (x) => selector(x).map(x => x._1.toLowerCase -> x._2).toMap
  }

  implicit def tableMapExtensions[T <: TableMapWithIdentity[U], U](q: Query[T, U]) = new SearchHelper(q)

  implicit def tupleExtensions[T <: Product, U](q: Query[T, U]) = new SearchHelperTuple(q)

  implicit def selectorExtensions[T, U](q: Query[T, U]) = new SearchHelperSelector(q)
}

trait Search {
  def withEntitySearch[T <: TableMapWithIdentity[U], U, C](q: Query[T, U], searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => x.id === ParseOp.parse[Int](searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => x.name.contains(searchOptions.query))
    else q
  }

  def withEntitySearchSelector[T, U](q: Query[T, U], searchOptions: SearchOptions)(selector: T => EntityColumnMapping): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => selector(x).id === ParseOp.parse[Int](searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => selector(x).name.contains(searchOptions.query))
    else q
  }

  def withEntitySearchTuple[T, U](q: Query[T, U], searchOptions: SearchOptions)(idNameSelector: T => (Column[Int], Column[String])): Query[T, U] = {
    if (searchOptions == null) return q
    if (ParseOp.canParse[Int](searchOptions.query)) q.filter(x => idNameSelector(x)._1 === ParseOp.parse[Int](searchOptions.query))
    else if (!searchOptions.query.isNullOrEmpty) q.filter(x => idNameSelector(x)._2.contains(searchOptions.query))
    else q
  }

  def withSortingForDto[T, U, C](q: Query[T, U], searchOptions: SearchOptions)(implicit ct: reflect.ClassTag[C]): Query[T, U] = {
    if (searchOptions == null) return q

    if (searchOptions.sortColumn != null && !searchOptions.sortColumn.isEmpty) {

      //get a map of dto property names and their indexes
      val ps = getParameterNameIndexMap[C]
      //get a map of qry columns and their indexes
      //val cs = getColumnIndexMap(q)

      // if (ps.toArray.length != cs.toArray.length) throw new Exception("Projection columns and dto properties should match.")

      if (ps.contains(searchOptions.sortColumn)) {
        if (searchOptions.sortDirection == "desc") q.sortBy(x => ps(searchOptions.sortColumn).desc)
        else q.sortBy(x => ps(searchOptions.sortColumn).asc)
      } else {
        q
      }
    } else {
      q
    }
  }

  def withPagingForDto[T, U](q: Query[T, U], searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    val ps = searchOptions.pageSize
    val pi = searchOptions.pageIndex

    if (ps.isDefined && pi.isDefined) {
      if (pi.get > 1) q.drop((pi.get - 1) * ps.get).take(ps.get)
      else q.take(ps.get)
    } else q
  }

  def withSortingAndPagingForDto[T, U, C](q: Query[T, U], searchOptions: SearchOptions)(implicit ct: reflect.ClassTag[C]) = {
    val total = withSortingForDto(q, searchOptions)(ct)
    val paged = withPagingForDto(total, searchOptions)
    PagedQuery(total, paged)
  }

  def getResultsForDto[T, U <: Object, C](q: Query[T, U], searchOptions: SearchOptions)(implicit session: Session, ct: reflect.ClassTag[C]) = {
    val query = withSortingAndPagingForDto(q, searchOptions)(ct)
    val results = query.paged.list.map(x => ClassOp.newInstance[C](getPropertyValues(x)))

    results
  }


  def withSorting[T, U, C](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => ColumnMapping): Query[T, U] = {
    if (searchOptions == null) return q
    import searchOptions._
    if (table.columnMap.contains(sortColumn)) {
      if (sortDirection == "desc") q.sortBy(selector(_).columnMap(sortColumn).desc)
      else q.sortBy(selector(_).columnMap(sortColumn).asc)
    } else {
      q
    }
  }

  def withSortingTuple[T, U, C](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => Map[String, Column[_]]): Query[T, U] = {
    if (searchOptions == null) return q
    import searchOptions._
    val sel = toKeyLowerCase(selector)

    if (table.columnMap.contains(sortColumn)) {
      if (sortDirection == "desc") q.sortBy(x => sel(x)(sortColumn).desc)
      else q.sortBy(x => sel(x)(sortColumn).asc)
    } else {
      q
    }
  }

  def withPaging[T, U, C](q: Query[T, U], searchOptions: SearchOptions): Query[T, U] = {
    if (searchOptions == null) return q
    import searchOptions._
    if (pageSize.isDefined && pageIndex.isDefined) {
      if (pageIndex.get > 1) q.drop((pageIndex.get - 1) * pageSize.get).take(pageSize.get)
      else q.take(pageSize.get)
    } else q
  }

  def withSortingAndPaging[T, U, C](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => ColumnMapping) = {
    val total = withSorting(q, table.instance, searchOptions)(selector)
    val paged = withPaging(total, searchOptions)
    PagedQuery(total, paged)
  }

  def withSortingAndPagingTuple[T, U, C](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => Map[String, Column[_]]) = {
    val total = withSortingTuple(q, table.instance, searchOptions)(selector)
    val paged = withPaging(total, searchOptions)
    PagedQuery(total, paged)
  }

  def getResults[T <: ColumnMapping, U, C](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(implicit session: Session) = {
    withSortingAndPaging(q, table, searchOptions)(t => t).paged.list
  }

  def getResultsSelector[T, U](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => ColumnMapping)(implicit session: Session) = {
    withSortingAndPaging(q, table, searchOptions)(selector).paged.list
  }


  def getResultsTuple[T <: Product, U](q: Query[T, U], table: ColumnMapping, searchOptions: SearchOptions)(selector: T => Map[String, Column[_]] /*= (x: T) => table.fromTuple(x)*/)(implicit session: Session) = {
    if (searchOptions == null) q.list
    else withSortingAndPagingTuple(q, table, searchOptions)(toKeyLowerCase(selector)).paged.list
  }

  def findAllAdvanced[T](table: Table[T] with ColumnMapping, query: List[SearchCondition], sortColumn: String, sortDirection: String, pageIndex: Option[Int], pageSize: Option[Int]): List[T] = {
    DB.withSession {
      implicit session =>

        var q = for (i <- table) yield i
        if (query != null) {
          def executeFilter[C](col: Column[C], p: SearchCondition)(implicit m: TypeTag[C],
                                                                   ms: TypeTag[Column[String]],
                                                                   mi: TypeTag[Column[Int]]) {
            if (ms == m) {
              val c = col.asInstanceOf[Column[String]]
              q = q.filter(x => if (p.matchType == MatchType.Exact) c === p.value else c.like((p.value)))
            } else if (mi == m) {
              val c = col.asInstanceOf[Column[Int]]
              //q = q.filter(_ => c == p.value)
            }
          }

          for (p <- query if table.columnMap.contains(p.columnName)) {
            val col = table.columnMap(p.columnName)
            executeFilter(col, p)
          }
        }

        if (table.columnMap.contains(sortColumn)) {
          val sortProp = table.columnMap(sortColumn);
          q = q.sortBy(e => if (sortDirection == "desc") sortProp.desc else sortProp.asc)
        }


        if (pageSize.isDefined && pageIndex.isDefined) {
          if (pageIndex.get > 1) q = q.drop((pageIndex.get - 1) * pageSize.get)
          q = q.take(pageSize.get)
        }


        q.list
    }
  }

  def getPropertyValues(obj: AnyRef): Array[AnyRef] = {
    (Array[AnyRef]() /: obj.getClass.getDeclaredFields) {
      (a, f) =>
        f.setAccessible(true)
        a :+ f.get(obj)
    }
  }

  def getParameterNameIndexMap[C](implicit ct: reflect.ClassTag[C]) = {
    var counter: Int = -1
    (Map[String, Int]() /: ct.runtimeClass.getDeclaredFields) {
      (a, f) =>
        counter = counter + 1
        f.setAccessible(true)
        a + (f.getName -> counter)
    }
  }


  def getColumnIndexMap[T, U](q: Query[T, U]) = {
    var counter: Int = -1
    (Map[Int, Any]() /: q.getClass.getDeclaredFields) {
      (a, f) =>
        f.setAccessible(true)
        counter = counter + 1
        a + (counter -> f)
    }
  }

  def toKeyLowerCase[T](selector: T => Map[String, Column[_]]): T => Map[String, Column[_]] = {
    (x) => selector(x).map(x => x._1.toLowerCase -> x._2).toMap
  }
}



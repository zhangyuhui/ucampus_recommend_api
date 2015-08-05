package com.moviedemo.mvc

import com.moviedemo.tools.ParseOp
import play.api.mvc._
import scala.Predef._
import scala.collection.mutable

object RequestHelper {
  object Int {
    def unapply(s: String): Option[Int] = try {
      Some(s.toInt)
    } catch {
      case _: java.lang.NumberFormatException => None
    }
  }

  object Long {
    def unapply(s: String): Option[Long] = try {
      Some(s.toLong)
    } catch {
      case _: java.lang.NumberFormatException => None
    }
  }

  object Boolean {
    def unapply(s: String): Option[Boolean] = try {
      Some(s.toBoolean)
    } catch {
      case _: java.lang.IllegalArgumentException => None
    }
  }

  def query(implicit request: Request[Any]): String = {
    getParamAsString("query")
  }

  def sortColumn(implicit request: Request[Any]): String = {
    getParamAsString("sort", toLowerCase = true)
  }

  def sortDirection(implicit request: Request[Any]): String = {
    val sortDirection = getParamAsString("dir")
    if (sortDirection == null) null
    else if ((sortDirection equalsIgnoreCase "asc") || (sortDirection equalsIgnoreCase "ascending")) "asc"
    else if ((sortDirection equalsIgnoreCase "desc") || (sortDirection equalsIgnoreCase "descending")) "desc"
    else null
  }

  def pageSize(implicit request: Request[Any]): Option[Int] = {
    val option = getParamAsInt("limit")
    if (option.isEmpty || option.get <= 0 || option.get > 100000) Option(50) else Option(option.get)
  }

  def pageIndex(implicit request: Request[Any]): Option[Int] = {
    val option = getParamAsInt("page")
    if (option.isEmpty || option.get < 1) Option(1) else Option(option.get)
  }

  def getParamAsString(key: String, toLowerCase: Boolean = false)(implicit request: Request[Any]): String = {
    val option = getQueryString(key)
    if (option.isEmpty) null else if (toLowerCase) option.get.toLowerCase else option.get
  }

  def getParamAsInt(key: String)(implicit request: Request[Any]): Option[Int] = getParamAsNumber[Int](key)

  def getParamAsNumber[T](key: String)(implicit request: Request[Any]): Option[T] = {
    val option = getQueryString(key)
    if (option.isEmpty) None
    else option.get match {
      case Int(x) => Some(x.asInstanceOf[T])
      case Long(x) => Some(x.asInstanceOf[T])
      case _ => None
    }
  }

  def getParamAsList[T](key: String)(implicit request: Request[Any], parseOp: ParseOp[T]): List[T] = {
    val options = getQueryString(key)
    val ids: mutable.MutableList[T] = mutable.MutableList()
    if (!options.isEmpty) {
      options.get.split(",").foreach((x: String) => if (ParseOp.canParse[T](x)) ids += ParseOp.parse[T](x).get)
    }
    ids.distinct.toList
  }

  def getParamAsBoolean(key: String)(implicit request: Request[Any]): Option[Boolean] = {
    val option = getQueryString(key)
    if (option.isEmpty) None
    else option.get match {
      case Boolean(x) => Some(x)
      case _ => None
    }
  }

  private def getQueryString(key: String)(implicit request: Request[Any]): Option[String] = {
    val option = request.getQueryString(key)
    if (option.isDefined) option
    else request.getQueryString(key.toLowerCase)
  }
}










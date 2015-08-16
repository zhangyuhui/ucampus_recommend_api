package com.ucampus.tools

import java.sql.SQLException

import play.api.Logger
import play.api.db.slick.Config.driver.simple._

import scala.slick.ast.{FunctionSymbol, Node}
import scala.slick.lifted.{Column, ExtensionMethods}

object DatabaseOp {
  val Regex = new FunctionSymbol("REGEXP")

  implicit class SessionExtensions(session: Session) {
    def withRecoveringTransaction[T](f: => T): T = {
      withRecoveringTransaction(f, 3)
    }

    def withRecoveringTransaction[T](f: => T, maxRetry: Int = 3): T = {
      try {
        session.withTransaction(f)
      } catch {
        case e: SQLException =>
          if (maxRetry <= 0) throw e
          else {
            Logger.warn(s"Sql fault in a self recovering transaction", e)
            Thread.sleep(100)
            withRecoveringTransaction(f, maxRetry - 1)
          }
      }
    }
  }

  /** Extension methods for Column[String] and Column[Option[String]] */
  implicit class StringColumnExtensionMethods[P1](val c: Column[P1]) extends AnyVal with ExtensionMethods[String, P1] {
    def regex[P2, R](e: Column[P2])(implicit om: o#arg[String, P2]#to[Boolean, R]) = om(
       Regex.column(n, Node(e)))
  }

  implicit class StringColumn(column: Column[String]) {

    /**
     * Escapes a string for a LIKE '% ... %' query
     * @param s
     */
    def contains(s: String) = column.like(s"%${s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_")}%")

    /**
     * Concatenates two string columns
     * @param otherColumn
     * @return The concatenation result column
     */
    def ||(otherColumn: Column[Option[String]]): Column[Option[String]] = {
      val concat = SimpleFunction.binary[Option[String], Option[String], Option[String]]("concat")
      concat(column, otherColumn)
    }
  }

  implicit class OptionStringColumn(column: Column[Option[String]]) {

    /**
     * Escapes a string for a LIKE '% ... %' query
     * @param s
     */
    def contains(s: String) = column.like(s"%${s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_")}%")

    /**
     * Concatenates two string columns
     * @param otherColumn
     * @return The concatenation result column
     */
    def ||(otherColumn: Column[Option[String]]): Column[Option[String]] = {
      val concat = SimpleFunction.binary[Option[String], Option[String], Option[String]]("concat")
      concat(column, otherColumn)
    }

    /**
     * Returns the string, or an empty string if NULL
     */
    def getOrEmpty = {
      val ifnull = SimpleFunction.binary[Option[String], String, String]("ifnull")
      ifnull(column, "")
    }
  }

  implicit class OptionColumn[T: TypeMapper](column: Column[Option[T]]) {

    def /(otherColumn: Column[_]): Column[Option[T]] = {
      val divide = SimpleBinaryOperator[Option[T]]("/")
      divide(column, otherColumn)
    }

    def *(otherColumn: Column[_]): Column[Option[T]] = {
      val multiply = SimpleBinaryOperator[Option[T]]("*")
      multiply(column, otherColumn)
    }

    def percentOf(otherColumn: Column[_]): Column[Option[T]] = {
      val divide = SimpleBinaryOperator[Option[T]]("/")
      val multiply = SimpleBinaryOperator[Option[T]]("*")
      multiply(divide(column, otherColumn), 100)
    }
  }

  /**
   * Divides the values of two columns. Returns a specified default if the computed value is NULL.
   * @param a The dividend column
   * @param b The divisor column
   * @param defaultValue The default quotient column if NULL
   * @tparam R The quotient type
   * @return The quotient or default column
   */
  def safeDivide[R: TypeMapper](a: Column[_], b: Column[_], defaultValue: Column[R]): Column[R] = {
    val ifnull = SimpleFunction.binary[R, R, R]("ifnull")
    val divide = SimpleBinaryOperator[R]("/")
    ifnull(divide(a, b), defaultValue)
  }

  /**
   * Subtracts the values of two columns. Returns a specified default if the computed value is NULL.
   * @param a
   * @param b
   * @param defaultValue The default result column if NULL
   * @tparam R The result type
   * @return The result or default column
   */
  def safeSubtract[R: TypeMapper](a: Column[_], b: Column[_], defaultValue: Column[R]): Column[R] = {
    val ifnull = SimpleFunction.binary[R, R, R]("ifnull")
    val subtract = SimpleBinaryOperator[R]("-")
    ifnull(subtract(a, b), defaultValue)
  }

  /**
   * Calculates a percentage. Returns zero if not divisible.
   * @param a
   * @param b
   * @param precision The number of decimal places to round to
   * @return The percentage
   */
  def safePercent(a: Column[_], b: Column[_], precision: Option[Int] = None): Column[Double] = {
    val ifnull = SimpleFunction.binary[Option[Double], Double, Double]("ifnull")
    val divide = SimpleBinaryOperator[Option[Double]]("/")
    val multiply = SimpleBinaryOperator[Option[Double]]("*")
    if (precision.isDefined) {
      val round = SimpleFunction.binary[Option[Double], Int, Option[Double]]("round")
      ifnull(round(multiply(divide(a, b), 100), precision.get), 0d)
    } else {
      ifnull(multiply(divide(a, b), 100), 0d)
    }
  }

  /**
   * Calculates a column's percentage deviation from another column
   * @param value The primary column
   * @param base The column to compare to
   * @return The % deviation of value from base
   */
  def percentDeviation(value: Column[_], base: Column[_]): Column[Option[Double]] = {
    val divide = SimpleBinaryOperator[Option[Double]]("/")
    val multiply = SimpleBinaryOperator[Option[Double]]("*")
    val subtract = SimpleBinaryOperator[Option[Double]]("-")
    subtract(multiply(divide(value, base), 100), 100)
  }

  def escapeSqlLike(s: String): String = {
    s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_").replace("'", "''")
  }
}

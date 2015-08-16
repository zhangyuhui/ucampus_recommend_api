package com.ucampus.data.maps

import scala.slick.lifted.Column

trait EntityColumnMapping extends ColumnMapping{
  def id: Column[Int];
  def name: Column[String];
}

trait ColumnMapping {
  def columnMap : Map[String,Column[_]]
  def instance = this
}

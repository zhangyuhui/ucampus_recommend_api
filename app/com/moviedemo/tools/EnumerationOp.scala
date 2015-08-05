package com.moviedemo.tools

object EnumerationOp {
  implicit def traversableConversions[T](xs: Traversable[T]) = new {

    def singleOrDefault(default: => T): T = {
      if (xs.isEmpty) default
      else if (xs.size > 1) sys error "More than one elements!"
      else xs.head
    }

    def firstOrDefault(default: => T): T = {
      if (xs.isEmpty) default
      else xs.head
    }

    def single: T = {
      if (xs.isEmpty) sys error "Collection is empty!"
      else if (xs.size > 1) sys error "More than one elements!"
      else xs.head
    }

    def first: T = {
      if (xs.isEmpty) sys error "Collection is empty!"
      else xs.head
    }

    def singleOption: Option[T] = {
      if (xs.isEmpty) None
      else if (xs.size > 1) sys error "More than one elements!"
      else Some(xs.head)
    }

    def firstOption: Option[T] = {
      if (xs.isEmpty) None
      else Some(xs.head)
    }
  }

  implicit def listConversions[T](xs: List[T]) = new {
    def noneIfEmpty: Option[List[T]] = if (xs.isEmpty) None else Some(xs)
  }

  implicit def optionListConversions[T](xs: Option[List[T]]) = new {
    def noneOrEmpty: Boolean = xs.isEmpty || xs.get.isEmpty
  }
}
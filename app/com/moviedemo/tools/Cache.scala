package com.moviedemo.tools

import play.api.Play.current
import scala.reflect.ClassTag
import com.moviedemo.settings.ApplicationSettings.Cache._

trait Cache {
  def getOrSet[A](key: String, expiration: Int = 0)(orElse: => A)(implicit ct: ClassTag[A]): A

  def get[A](key: String): Option[A]

  def set[A](key: String, value: A, expiration: Int = 0)

  def getAndRemove[A](key: String): Option[A]

  def remove(key: String)
}

class CacheImpl extends Cache {
  private val apiVersion: String = current.configuration.getString("api.version") match {
    case None => ""
    case Some(value) => value.last + "_"
  }

  private def getKey(key: String) = apiVersion + key

  override def getOrSet[A](key: String, expiration: Int = cacheMedium)(orElse: => A)(implicit ct: ClassTag[A]): A = {
    val r = get(key)
    if (r.isDefined) r.get.asInstanceOf[A]
    else {
      val value = orElse
      if (value != null) set(key, value, expiration)
      value
    }
  }

  override def get[A](key: String): Option[A] = {
    play.api.cache.Cache.get(getKey(key)) match {
      case r: Option[A] => r
      case _ => None
    }
  }

  override def set[A](key: String, value: A, expiration: Int = cacheMedium) = {
    play.api.cache.Cache.set(getKey(key), value, Math.max(expiration, cacheMax))
  }

  override def getAndRemove[A](key: String) = {
    val result = get[A](key)
    remove(key)
    result
  }

  override def remove(key: String) = {
    play.api.cache.Cache.remove(getKey(key))
  }
}
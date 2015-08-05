package com.moviedemo.tools

import com.google.inject.Inject
import com.moviedemo.settings.ApplicationSettings.Cache._
import scala.reflect.ClassTag

trait ResourceCache {
  def getOrSet[T](id: Int, expiration: Int = 0)(orElse: => Option[T])(implicit ct: ClassTag[T]): Option[T]

  def get[T](id: Int)(implicit ct: ClassTag[T]): Option[T]

  def set[T <: {val id: Int}](value: T, expiration: Int = 0)(implicit ct: ClassTag[T])

  def getAndRemove[T](id: Int)(implicit ct: ClassTag[T]): Option[T]

  def remove[T](id: Int)(implicit ct: ClassTag[T])
}

class ResourceCacheImpl @Inject()(cache: Cache) extends ResourceCache {
  override def getOrSet[T](id: Int, expiration: Int = defaultExpiration)(orElse: => Option[T])(implicit ct: ClassTag[T]): Option[T] = {
    val key = makeKey[T](id)
    cache.get(key) orElse {
      orElse map { obj =>
        cache.set(key, obj, expiration)
        obj
      }
    }
  }

  override def get[T](id: Int)(implicit ct: ClassTag[T]): Option[T] = cache.get(makeKey[T](id))

  override def set[T <: {val id: Int}](value: T, expiration: Int = defaultExpiration)(implicit ct: ClassTag[T]) = cache.set(makeKey[T](value.id), value, expiration)

  override def getAndRemove[T](id: Int)(implicit ct: ClassTag[T]) = cache.getAndRemove(makeKey[T](id))

  override def remove[T](id: Int)(implicit ct: ClassTag[T]) = cache.remove(makeKey[T](id))

  private def makeKey[T](id: Int)(implicit ct: ClassTag[T]) = s"${ct.runtimeClass.getName}:$id"

  private[tools] def defaultExpiration = cacheMedium
}
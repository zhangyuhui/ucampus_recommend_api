package com.moviedemo.settings

import com.google.inject.{ConfigurationException, Injector}
import play.api.Configuration
import play.api.Play._

object ApplicationSettings {

  def getWithDefault[T](defaultValue: T)(f: Configuration => Option[T]): T = {
    maybeGet(f).getOrElse(defaultValue)
  }

  def get[T](f: Configuration => Option[T]): T = {
    maybeGet(f).get
  }

  def maybeGet[T](f: Configuration => Option[T]): Option[T] = {
    maybeApplication.flatMap(a => f(a.configuration))
  }

  object Databases {
    def DB = play.api.db.slick.DB
  }

  object Cache {
    def cacheShort: Int = getWithDefault(20)(_.getInt("cache.short"))

    def cacheMedium: Int = getWithDefault(120)(_.getInt("cache.medium"))

    def cacheLong: Int = getWithDefault(86400)(_.getInt("cache.long"))

    def cacheToken: Int = getWithDefault(21600)(_.getInt("cache.token"))

    def cacheMax : Int =  getWithDefault(86400)(_.getInt("cache.max"))

    def cacheCookie : Int = getWithDefault(86400)(_.getInt("cache.cookie"))
  }
}

object InjectorContainer {
  private var _instance: Injector = null

  def instance = _instance

  def instance(value: Injector) {
    if (_instance != null) throw new Exception("Dependency container can be initialized only once.")
    _instance = value
  }
}

trait Inject {
  def getInstance[T](implicit manifest: Manifest[T]) = InjectorContainer.instance.getInstance(manifest.runtimeClass.asInstanceOf[Class[T]])

  def getInstance[T](clazz: Class[T]) = InjectorContainer.instance.getInstance(clazz)

  def getMaybeInstance[T](implicit manifest: Manifest[T]): Option[T] = try {
    if (InjectorContainer.instance != null)
      Option(InjectorContainer.instance.getInstance(manifest.runtimeClass.asInstanceOf[Class[T]]))
    else None
  } catch {
    case x: ConfigurationException => None
  }
}

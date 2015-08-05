package com.moviedemo.tools

import org.joda.time.{Duration, Days, DateTime, DateTimeZone}

object DateTimeOp {
  /**
   * @return The datetime for date key
   */
  def dateKeyToDateTime(dateKey: Option[Int], timezone: Option[DateTimeZone] = None): Option[DateTime] = {
    //Using UTC timezone in Datetime Constructor as toStandardDuration uses UTC to get millis and default timezone equivalent is obtained
    if (dateKey.isDefined && dateKey.get >= 0) Some(new DateTime(Days.days(dateKey.get).toStandardDuration.getMillis, DateTimeZone.UTC).withZoneRetainFields(timezone.getOrElse(DateTimeZone.getDefault)))
    else None
  }

  def dateTimeToDateKey(dateTime: Option[DateTime]): Option[Int] = {
    if (dateTime.isDefined) Some(Duration.millis(dateTime.get.withZoneRetainFields(DateTimeZone.UTC).getMillis).getStandardDays.toInt)
    else None
  }

  /**
   * @return The date key for today with timezone and delta
   */
  private def dateKey(tz: Option[DateTimeZone] = None, daysDelta: Int = 0) = dateTimeToDateKey(Some(tz.map(DateTime.now(_)).getOrElse(DateTime.now).plusDays(daysDelta))).get

  def todayKey(tz: Option[DateTimeZone]): Int = dateKey(tz, 0)

  def todayKey: Int = todayKey(None)

  def tomorrowKey(tz: Option[DateTimeZone] = None) = dateKey(tz, 1)

  def dayAfterTomorrowKey(tz: Option[DateTimeZone] = None) = dateKey(tz, 2)

  @deprecated("Just use zero", "")
  def beginningKey = dateTimeToDateKey(Some(new DateTime(0))).get

  implicit class DateTimeHelper(dateTime: DateTime) {
    def endOfDay = dateTime.withTime(23, 59, 59, 0)

    def beginningOfDay = dateTime.withTime(0, 0, 0, 0)

    def daysDiff(endDate: DateTime): Int =
      Days.daysBetween(dateTime.withTimeAtStartOfDay, endDate.withTimeAtStartOfDay).getDays

    def toDateKey = dateTimeToDateKey(Some(dateTime)).get

    /**
     * Gets the minutes of the datetime instant from the Java epoch
     * of 1970-01-01T00:00:00Z.
     *
     * @return the number of minutes since 1970-01-01T00:00:00Z
     */

    def getMinutes = (dateTime.getMillis / 60000).toInt // 60 * 1000
  }

  implicit class OptionDateTimeHelper(dateTime: Option[DateTime]) {
    def endOfDay: Option[DateTime] = if (dateTime.isDefined) Some(dateTime.get.endOfDay) else None

    def beginningOfDay: Option[DateTime] = if (dateTime.isDefined) Some(dateTime.get.beginningOfDay) else None

    def toDateKey = dateTimeToDateKey(dateTime)
  }

  implicit class DateKeyHelper(dateKey: Int) {
    def toDateTime(timezone: Option[DateTimeZone] = None) = dateKeyToDateTime(Some(dateKey), timezone).get
  }

  implicit class OptionDateKeyHelper(dateKey: Option[Int]) {
    def toDateTime(timezone: Option[DateTimeZone] = None) = dateKeyToDateTime(dateKey, timezone)
  }
}



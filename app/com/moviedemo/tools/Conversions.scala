package com.moviedemo.tools

import com.moviedemo.mvc.ServiceResult
import play.api.libs.json._
import scala.collection.{mutable => m}
import scala.reflect.ClassTag
import org.joda.time.DateTime

object Conversions {

  class Empty() {}

  implicit class OptionToBoolean(value : Option[Boolean]){
    def falseIfEmpty = Option(value.getOrElse(false))
    def trueIfEmpty = Option(value.getOrElse(true))
  }

  def empty = new Empty()

  implicit def timestampToDateTime(value: java.sql.Timestamp): DateTime = new DateTime(value.getTime)

  implicit def seqToList[T](value: Seq[T]): List[T] = value.toList

  implicit def iterableToSeq[T](value: Iterable[T]): Seq[T] = value.toSeq

  implicit def optionToValue[T](option: Option[T]): T = {
    if (option.isEmpty) sys error "Option was not expected to be empty. Verify that you have the correct data (for example database, etc)"
    option.get
  }

  implicit def valueToOption[T](value: T): Option[T] = {
    Some(value)
  }

  implicit def valueToValue[T](value: T): T = {
    value
  }

  implicit def emptyToOption[T](empty: Empty): Option[T] = {
    None
  }

  implicit def getErrors(errors: JsError): String = {
    errors.errors map {
      x => "" + x._1.path + " : " + x._2.map(_.message).mkString("; ")
    } mkString sys.props("line.separator")
  }

  implicit def doubleToString(value: Double): String = value.toString
  implicit def floatToString(value: Float): String = value.toString
  implicit def longToString(value: Long): String = value.toString
  implicit def intToString(value: Int): String = value.toString
  implicit def shortToString(value: Short): String = value.toString
  implicit def booleanToString(value: Boolean): String = value.toString

  implicit def optionToOption[T](value: Option[T]): Option[T] = {
    value
  }

  implicit def entityToResult[T](value: T): ServiceResult[T] = ServiceResult(value)

  implicit def tupleToTuple2[T1, T2](t: (T1, T2)) = new {
    implicit def typeSafe[A1, A2](implicit f1: T1 => A1, f2: T2 => A2): (A1, A2) = {
      (t._1, t._2)
    }
  }

  implicit def tupleToTuple3[
  T1, T2, T3](t: (T1, T2, T3)) = new {
    implicit def typeSafe[A1, A2, A3](implicit
                                      f1: T1 => A1,
                                      f2: T2 => A2,
                                      f3: T3 => A3): (A1, A2, A3) = {
      (t._1, t._2, t._3)
    }
  }

  implicit def tupleToTuple4[
  T1, T2, T3, T4
  ](t: (T1, T2, T3, T4)) = new {
    implicit def typeSafe[A1, A2, A3, A4](implicit
                                          f1: T1 => A1,
                                          f2: T2 => A2,
                                          f3: T3 => A3,
                                          f4: T4 => A4): (A1, A2, A3, A4) = {
      (t._1, t._2, t._3, t._4)
    }
  }


  implicit def tupleToTuple5[
  T1, T2, T3, T4, T5
  ](t: (T1, T2, T3, T4, T5)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5](implicit
                                              f1: T1 => A1,
                                              f2: T2 => A2,
                                              f3: T3 => A3,
                                              f4: T4 => A4,
                                              f5: T5 => A5): (A1, A2, A3, A4, A5) = {
      (t._1, t._2, t._3, t._4, t._5)
    }
  }

  implicit def tupleToTuple6[
  T1, T2, T3, T4, T5, T6
  ](t: (T1, T2, T3, T4, T5, T6)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6](implicit
                                                  f1: T1 => A1,
                                                  f2: T2 => A2,
                                                  f3: T3 => A3,
                                                  f4: T4 => A4,
                                                  f5: T5 => A5,
                                                  f6: T6 => A6): (A1, A2, A3, A4, A5, A6) = {
      (t._1, t._2, t._3, t._4, t._5, t._6)
    }
  }

  implicit def tupleToTuple7[
  T1, T2, T3, T4, T5, T6, T7
  ](t: (T1, T2, T3, T4, T5, T6, T7)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7](implicit
                                                      f1: T1 => A1,
                                                      f2: T2 => A2,
                                                      f3: T3 => A3,
                                                      f4: T4 => A4,
                                                      f5: T5 => A5,
                                                      f6: T6 => A6,
                                                      f7: T7 => A7): (A1, A2, A3, A4, A5, A6, A7) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7)
    }
  }

  implicit def tupleToTuple8[
  T1, T2, T3, T4, T5, T6, T7, T8
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8](implicit
                                                          f1: T1 => A1,
                                                          f2: T2 => A2,
                                                          f3: T3 => A3,
                                                          f4: T4 => A4,
                                                          f5: T5 => A5,
                                                          f6: T6 => A6,
                                                          f7: T7 => A7,
                                                          f8: T8 => A8): (A1, A2, A3, A4, A5, A6, A7, A8) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8)
    }
  }

  implicit def tupleToTuple9[
  T1, T2, T3, T4, T5, T6, T7, T8, T9
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9](implicit
                                                              f1: T1 => A1,
                                                              f2: T2 => A2,
                                                              f3: T3 => A3,
                                                              f4: T4 => A4,
                                                              f5: T5 => A5,
                                                              f6: T6 => A6,
                                                              f7: T7 => A7,
                                                              f8: T8 => A8,
                                                              f9: T9 => A9): (A1, A2, A3, A4, A5, A6, A7, A8, A9) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9)
    }
  }

  implicit def tupleToTuple10[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10](implicit
                                                                   f1: T1 => A1,
                                                                   f2: T2 => A2,
                                                                   f3: T3 => A3,
                                                                   f4: T4 => A4,
                                                                   f5: T5 => A5,
                                                                   f6: T6 => A6,
                                                                   f7: T7 => A7,
                                                                   f8: T8 => A8,
                                                                   f9: T9 => A9,
                                                                   f10: T10 => A10): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10)
    }
  }


  implicit def tupleToTuple11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11]
  (t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11](implicit
                                                                        f1: T1 => A1,
                                                                        f2: T2 => A2,
                                                                        f3: T3 => A3,
                                                                        f4: T4 => A4,
                                                                        f5: T5 => A5,
                                                                        f6: T6 => A6,
                                                                        f7: T7 => A7,
                                                                        f8: T8 => A8,
                                                                        f9: T9 => A9,
                                                                        f10: T10 => A10,
                                                                        f11: T11 => A11): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11)
    }
  }

  implicit def tupleToTuple12[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12](implicit
                                                                             f1: T1 => A1,
                                                                             f2: T2 => A2,
                                                                             f3: T3 => A3,
                                                                             f4: T4 => A4,
                                                                             f5: T5 => A5,
                                                                             f6: T6 => A6,
                                                                             f7: T7 => A7,
                                                                             f8: T8 => A8,
                                                                             f9: T9 => A9,
                                                                             f10: T10 => A10,
                                                                             f11: T11 => A11,
                                                                             f12: T12 => A12): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12)
    }
  }

  implicit def tupleToTuple13[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13](implicit
                                                                                  f1: T1 => A1,
                                                                                  f2: T2 => A2,
                                                                                  f3: T3 => A3,
                                                                                  f4: T4 => A4,
                                                                                  f5: T5 => A5,
                                                                                  f6: T6 => A6,
                                                                                  f7: T7 => A7,
                                                                                  f8: T8 => A8,
                                                                                  f9: T9 => A9,
                                                                                  f10: T10 => A10,
                                                                                  f11: T11 => A11,
                                                                                  f12: T12 => A12,
                                                                                  f13: T13 => A13): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13)
    }
  }

  implicit def tupleToTuple14[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14](implicit
                                                                                       f1: T1 => A1,
                                                                                       f2: T2 => A2,
                                                                                       f3: T3 => A3,
                                                                                       f4: T4 => A4,
                                                                                       f5: T5 => A5,
                                                                                       f6: T6 => A6,
                                                                                       f7: T7 => A7,
                                                                                       f8: T8 => A8,
                                                                                       f9: T9 => A9,
                                                                                       f10: T10 => A10,
                                                                                       f11: T11 => A11,
                                                                                       f12: T12 => A12,
                                                                                       f13: T13 => A13,
                                                                                       f14: T14 => A14): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14)
    }
  }

  implicit def tupleToTuple15[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15](implicit
                                                                                            f1: T1 => A1,
                                                                                            f2: T2 => A2,
                                                                                            f3: T3 => A3,
                                                                                            f4: T4 => A4,
                                                                                            f5: T5 => A5,
                                                                                            f6: T6 => A6,
                                                                                            f7: T7 => A7,
                                                                                            f8: T8 => A8,
                                                                                            f9: T9 => A9,
                                                                                            f10: T10 => A10,
                                                                                            f11: T11 => A11,
                                                                                            f12: T12 => A12,
                                                                                            f13: T13 => A13,
                                                                                            f14: T14 => A14,
                                                                                            f15: T15 => A15): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15)
    }
  }

  implicit def tupleToTuple16[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16](implicit
                                                                                                 f1: T1 => A1,
                                                                                                 f2: T2 => A2,
                                                                                                 f3: T3 => A3,
                                                                                                 f4: T4 => A4,
                                                                                                 f5: T5 => A5,
                                                                                                 f6: T6 => A6,
                                                                                                 f7: T7 => A7,
                                                                                                 f8: T8 => A8,
                                                                                                 f9: T9 => A9,
                                                                                                 f10: T10 => A10,
                                                                                                 f11: T11 => A11,
                                                                                                 f12: T12 => A12,
                                                                                                 f13: T13 => A13,
                                                                                                 f14: T14 => A14,
                                                                                                 f15: T15 => A15,
                                                                                                 f16: T16 => A16): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16)
    }
  }

  implicit def tupleToTuple17[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17](implicit
                                                                                                      f1: T1 => A1,
                                                                                                      f2: T2 => A2,
                                                                                                      f3: T3 => A3,
                                                                                                      f4: T4 => A4,
                                                                                                      f5: T5 => A5,
                                                                                                      f6: T6 => A6,
                                                                                                      f7: T7 => A7,
                                                                                                      f8: T8 => A8,
                                                                                                      f9: T9 => A9,
                                                                                                      f10: T10 => A10,
                                                                                                      f11: T11 => A11,
                                                                                                      f12: T12 => A12,
                                                                                                      f13: T13 => A13,
                                                                                                      f14: T14 => A14,
                                                                                                      f15: T15 => A15,
                                                                                                      f16: T16 => A16,
                                                                                                      f17: T17 => A17): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17)
    }
  }

  implicit def tupleToTuple18[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18](implicit
                                                                                                           f1: T1 => A1,
                                                                                                           f2: T2 => A2,
                                                                                                           f3: T3 => A3,
                                                                                                           f4: T4 => A4,
                                                                                                           f5: T5 => A5,
                                                                                                           f6: T6 => A6,
                                                                                                           f7: T7 => A7,
                                                                                                           f8: T8 => A8,
                                                                                                           f9: T9 => A9,
                                                                                                           f10: T10 => A10,
                                                                                                           f11: T11 => A11,
                                                                                                           f12: T12 => A12,
                                                                                                           f13: T13 => A13,
                                                                                                           f14: T14 => A14,
                                                                                                           f15: T15 => A15,
                                                                                                           f16: T16 => A16,
                                                                                                           f17: T17 => A17,
                                                                                                           f18: T18 => A18): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18)
    }
  }

  implicit def tupleToTuple19[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19](implicit
                                                                                                                f1: T1 => A1,
                                                                                                                f2: T2 => A2,
                                                                                                                f3: T3 => A3,
                                                                                                                f4: T4 => A4,
                                                                                                                f5: T5 => A5,
                                                                                                                f6: T6 => A6,
                                                                                                                f7: T7 => A7,
                                                                                                                f8: T8 => A8,
                                                                                                                f9: T9 => A9,
                                                                                                                f10: T10 => A10,
                                                                                                                f11: T11 => A11,
                                                                                                                f12: T12 => A12,
                                                                                                                f13: T13 => A13,
                                                                                                                f14: T14 => A14,
                                                                                                                f15: T15 => A15,
                                                                                                                f16: T16 => A16,
                                                                                                                f17: T17 => A17,
                                                                                                                f18: T18 => A18,
                                                                                                                f19: T19 => A19
                                                                                                                 ): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19)
    }
  }


  implicit def tupleToTuple20[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20](implicit
                                                                                                                     f1: T1 => A1,
                                                                                                                     f2: T2 => A2,
                                                                                                                     f3: T3 => A3,
                                                                                                                     f4: T4 => A4,
                                                                                                                     f5: T5 => A5,
                                                                                                                     f6: T6 => A6,
                                                                                                                     f7: T7 => A7,
                                                                                                                     f8: T8 => A8,
                                                                                                                     f9: T9 => A9,
                                                                                                                     f10: T10 => A10,
                                                                                                                     f11: T11 => A11,
                                                                                                                     f12: T12 => A12,
                                                                                                                     f13: T13 => A13,
                                                                                                                     f14: T14 => A14,
                                                                                                                     f15: T15 => A15,
                                                                                                                     f16: T16 => A16,
                                                                                                                     f17: T17 => A17,
                                                                                                                     f18: T18 => A18,
                                                                                                                     f19: T19 => A19,
                                                                                                                     f20: T20 => A20
                                                                                                                      ): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20)
    }
  }

  implicit def tupleToTuple21[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21](implicit
                                                                                                                          f1: T1 => A1,
                                                                                                                          f2: T2 => A2,
                                                                                                                          f3: T3 => A3,
                                                                                                                          f4: T4 => A4,
                                                                                                                          f5: T5 => A5,
                                                                                                                          f6: T6 => A6,
                                                                                                                          f7: T7 => A7,
                                                                                                                          f8: T8 => A8,
                                                                                                                          f9: T9 => A9,
                                                                                                                          f10: T10 => A10,
                                                                                                                          f11: T11 => A11,
                                                                                                                          f12: T12 => A12,
                                                                                                                          f13: T13 => A13,
                                                                                                                          f14: T14 => A14,
                                                                                                                          f15: T15 => A15,
                                                                                                                          f16: T16 => A16,
                                                                                                                          f17: T17 => A17,
                                                                                                                          f18: T18 => A18,
                                                                                                                          f19: T19 => A19,
                                                                                                                          f20: T20 => A20,
                                                                                                                          f21: T21 => A21
                                                                                                                           ): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20, t._21)
    }
  }

  implicit def tupleToTuple22[
  T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22
  ](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)) = new {
    implicit def typeSafe[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22](implicit
                                                                                                                               f1: T1 => A1,
                                                                                                                               f2: T2 => A2,
                                                                                                                               f3: T3 => A3,
                                                                                                                               f4: T4 => A4,
                                                                                                                               f5: T5 => A5,
                                                                                                                               f6: T6 => A6,
                                                                                                                               f7: T7 => A7,
                                                                                                                               f8: T8 => A8,
                                                                                                                               f9: T9 => A9,
                                                                                                                               f10: T10 => A10,
                                                                                                                               f11: T11 => A11,
                                                                                                                               f12: T12 => A12,
                                                                                                                               f13: T13 => A13,
                                                                                                                               f14: T14 => A14,
                                                                                                                               f15: T15 => A15,
                                                                                                                               f16: T16 => A16,
                                                                                                                               f17: T17 => A17,
                                                                                                                               f18: T18 => A18,
                                                                                                                               f19: T19 => A19,
                                                                                                                               f20: T20 => A20,
                                                                                                                               f21: T21 => A21,
                                                                                                                               f22: T22 => A22
                                                                                                                                ): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20, t._21, t._22)
    }
  }

  def convertSingle[T, R](handler: Option[T])(converter: T => R)(implicit classTag: ClassTag[T], dependencies: m.Map[Any, List[Any]]): Option[R] = {
    handler match {
      case Some(x) => {
        val clazz = classTag.runtimeClass

        if (dependencies.contains(clazz)) dependencies(clazz) = x :: dependencies(clazz)
        else dependencies += clazz -> List(x)

        x match {
          case i: T => converter(i)
        }
      }
      case _ => None
    }
  }

  def convertMany[T, R](handler: List[T])(converter: T => R)(implicit classTag: ClassTag[T], dependencies: m.Map[Any, List[Any]]): Option[List[R]] = {
    handler match {
      case x if !x.isEmpty => {
        val clazz = classTag.runtimeClass

        if (dependencies.contains(clazz)) dependencies(clazz) = dependencies(clazz) ::: x
        else dependencies += clazz -> x

        x.map(i => converter(i))
      }
      case _ => None
    }
  }

  def convertFrom[T](implicit classTag: ClassTag[T], dependencies: m.Map[Any, List[Any]]): List[T] = {
    dependencies.get(classTag.runtimeClass).getOrElse(Nil).map(_.asInstanceOf[T]).toList
  }
}

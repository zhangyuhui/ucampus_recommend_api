package com.moviedemo.data.maps

object TableConversion {

  implicit def t2Apply[T1, T2, O, D](func: (T1, T2, Option[O]) => D) = new {
    implicit def << : (T1, T2) => D = {
      (t1, t2) => func(t1, t2, None)
    }
  }

  implicit def t2UnApply[T1, T2, O, D](func: D => Option[(T1, T2, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2)
    }
  }

  implicit def t3Apply[T1, T2, T3, O, D](func: (T1, T2, T3, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3) => D = {
      (t1, t2, t3) => func(t1, t2, t3, None)
    }
  }

  implicit def t3UnApply[T1, T2, T3, O, D](func: D => Option[(T1, T2, T3, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3)
    }
  }

  implicit def t4Apply[T1, T2, T3, T4, O, D](func: (T1, T2, T3, T4, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4) => D = {
      (t1, t2, t3, t4) => func(t1, t2, t3, t4, None)
    }
  }

  implicit def t4UnApply[T1, T2, T3, T4, O, D](func: D => Option[(T1, T2, T3, T4, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4)
    }
  }

  implicit def t5Apply[T1, T2, T3, T4, T5, O, D](func: (T1, T2, T3, T4, T5, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5) => D = {
      (t1, t2, t3, t4, t5) => func(t1, t2, t3, t4, t5, None)
    }
  }

  implicit def t5UnApply[T1, T2, T3, T4, T5, O, D](func: D => Option[(T1, T2, T3, T4, T5, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5)
    }
  }

  implicit def t6Apply[T1, T2, T3, T4, T5, T6, O, D](func: (T1, T2, T3, T4, T5, T6, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6) => D = {
      (t1, t2, t3, t4, t5, t6) => func(t1, t2, t3, t4, t5, t6, None)
    }
  }

  implicit def t6UnApply[T1, T2, T3, T4, T5, T6, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6)
    }
  }

  implicit def t7Apply[T1, T2, T3, T4, T5, T6, T7, O, D](func: (T1, T2, T3, T4, T5, T6, T7, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7) => D = {
      (t1, t2, t3, t4, t5, t6, t7) => func(t1, t2, t3, t4, t5, t6, t7, None)
    }
  }

  implicit def t7UnApply[T1, T2, T3, T4, T5, T6, T7, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7)
    }
  }

  implicit def t8Apply[T1, T2, T3, T4, T5, T6, T7, T8, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8) => func(t1, t2, t3, t4, t5, t6, t7, t8, None)
    }
  }

  implicit def t8UnApply[T1, T2, T3, T4, T5, T6, T7, T8, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8)
    }
  }

  implicit def t9Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, None)
    }
  }

  implicit def t9UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9)
    }
  }

  implicit def t10Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, None)
    }
  }

  implicit def t10UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10)
    }
  }

  implicit def t11Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, None)
    }
  }

  implicit def t11UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11)
    }
  }

  implicit def t12Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, None)
    }
  }

  implicit def t12UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12)
    }
  }

  implicit def t13Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, None)
    }
  }

  implicit def t13UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13)
    }
  }

  implicit def t14Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, None)
    }
  }

  implicit def t14UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14)
    }
  }

  implicit def t15Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, None)
    }
  }

  implicit def t15UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15)
    }
  }

  implicit def t16Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, None)
    }
  }

  implicit def t16UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16)
    }
  }

  implicit def t17Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, None)
    }
  }

  implicit def t17UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16, r._17)
    }
  }

  implicit def t18Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, None)
    }
  }

  implicit def t18UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16, r._17, r._18)
    }
  }

  implicit def t19Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, None)
    }
  }

  implicit def t19UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16, r._17, r._18, r._19)
    }
  }

  implicit def t20Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, None)
    }
  }

  implicit def t20UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16, r._17, r._18, r._19, r._20)
    }
  }


  implicit def t21Apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, O, D](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21,Option[O]) => D) = new {
    implicit def << : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 ,T21) => D = {
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21) => func(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, None)
    }
  }

  implicit def t21UnApply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, O, D](func: D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, Option[O])]) = new {
    implicit def << : D => Option[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = {
      (d) =>
        val r = func(d).get
        Some(r._1, r._2, r._3, r._4, r._5, r._6, r._7, r._8, r._9, r._10, r._11, r._12, r._13, r._14, r._15, r._16, r._17, r._18, r._19, r._20, r._21)
    }
  }
}

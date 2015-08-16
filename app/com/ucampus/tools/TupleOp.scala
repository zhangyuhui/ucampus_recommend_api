package com.ucampus.tools

object TupleOp {
  implicit def t2[T1, T2](t: (T1, T2)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2)
    }
  }

  implicit def t3[T1, T2, T3](t: (T1, T2, T3)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3)
    }
  }

  implicit def t4[T1, T2, T3, T4](t: (T1, T2, T3, T4)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4)
    }
  }


  implicit def t5[T1, T2, T3, T4, T5](t: (T1, T2, T3, T4, T5)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5)
    }
  }

  implicit def t6[T1, T2, T3, T4, T5, T6](t: (T1, T2, T3, T4, T5, T6)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6)
    }
  }

  implicit def t7[T1, T2, T3, T4, T5, T6, T7](t: (T1, T2, T3, T4, T5, T6, T7)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7)
    }
  }

  implicit def t8[T1, T2, T3, T4, T5, T6, T7, T8](t: (T1, T2, T3, T4, T5, T6, T7, T8)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8)
    }
  }

  implicit def t9[T1, T2, T3, T4, T5, T6, T7, T8, T9](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9)
    }
  }

  implicit def t10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10)
    }
  }

  implicit def t11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11)
    }
  }

  implicit def t12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12)
    }
  }

  implicit def t13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13)
    }
  }

  implicit def t14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14)
    }
  }

  implicit def t15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15)
    }
  }

  implicit def t16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16)
    }
  }


  implicit def t17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17)
    }
  }

  implicit def t18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18)
    }
  }

  implicit def t19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19)
    }
  }

  implicit def t20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20)
    }
  }

  implicit def t21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](t: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)) = new {
    def ~[T](arg: T) = {
      (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20, t._21, arg)
    }

    def ~:[T](arg: T) = {
      (arg, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, t._17, t._18, t._19, t._20, t._21)
    }
  }

  implicit def default[T]: Option[T] = None
}

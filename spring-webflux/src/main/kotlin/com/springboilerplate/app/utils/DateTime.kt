package com.springboilerplate.app.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateTime {
    fun convertStrTime(str: String?): Date? {
        if (str != null) {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val strReplace = str.substring(0, 19).replace(Regex("""[TZ]"""), " ")
            return format.parse(strReplace)
        }
        return null
    }
}
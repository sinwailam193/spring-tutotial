package com.springboilerplate.app.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTime {
    fun convertStrTime(str: String?): LocalDateTime? {
        if (str != null) {
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(str.substring(0, 19), format)
        }
        return null
    }
}
package com.springboilerplate.app.models

import java.time.LocalDateTime

open class BaseModel {
    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime
    var deletedAt: LocalDateTime? = null
}
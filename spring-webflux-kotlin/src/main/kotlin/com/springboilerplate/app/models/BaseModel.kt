package com.springboilerplate.app.models

import java.util.Date

open class BaseModel {
    lateinit var createdAt: Date
    lateinit var updatedAt: Date
    var deletedAt: Date? = null
}
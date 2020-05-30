package com.springboilerplate.app.models

data class User(
        val id: Long,
        val name: String,
        val email: String,
        val provider: Provider
) : BaseModel()
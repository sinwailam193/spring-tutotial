package com.springboilerplate.app.repo.user

import com.springboilerplate.app.models.User

interface UserRepo {
    suspend fun fetchUser(id: Long): User?
}
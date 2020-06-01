package com.springboilerplate.app.services

import com.springboilerplate.app.models.User
import com.springboilerplate.app.repo.user.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepo: UserRepo

    suspend fun fetchUser(id: Long): User? = userRepo.fetchUser(id)
}
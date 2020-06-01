package com.springboilerplate.app.resolvers

import com.expediagroup.graphql.spring.operations.Query
import com.springboilerplate.app.models.User
import com.springboilerplate.app.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserQuery : Query {
    @Autowired
    lateinit var userService: UserService

    suspend fun getPerson(id: Long): User? = userService.fetchUser(id)
}
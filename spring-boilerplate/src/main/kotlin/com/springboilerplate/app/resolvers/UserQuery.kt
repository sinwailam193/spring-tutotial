package com.springboilerplate.app.resolvers

import com.springboilerplate.app.models.User
import com.springboilerplate.app.security.UserPrincipal
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserQuery : GraphQLQueryResolver {
    fun me(): User   {
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return userPrincipal.user
    }
}
package com.springboilerplate.app.resolvers.query

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class Test : GraphQLQueryResolver {
    fun hello(): String = "World"
}
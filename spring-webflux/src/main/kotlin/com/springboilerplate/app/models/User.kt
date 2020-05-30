package com.springboilerplate.app.models

import com.expediagroup.graphql.annotations.GraphQLName
import com.fasterxml.jackson.annotation.JsonProperty

data class User(
        val id: Long,
        val name: String,
        val email: String,
        val provider: Provider
) : BaseModel()
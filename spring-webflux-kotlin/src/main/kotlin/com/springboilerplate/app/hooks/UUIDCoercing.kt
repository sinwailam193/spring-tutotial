package com.springboilerplate.app.hooks

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import java.util.UUID

object UUIDCoercing : Coercing<UUID, String> {
        override fun parseValue(input: Any?): UUID = UUID.fromString(serialize(input))

        override fun parseLiteral(input: Any?): UUID? {
            val uuidString = (input as? StringValue)?.value
            return UUID.fromString(uuidString)
        }

        override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult.toString()

        val graphqlUUIDType: GraphQLScalarType = GraphQLScalarType.newScalar()
            .name("UUID")
            .description("A type representing a formatted java.util.UUID")
            .coercing(UUIDCoercing)
            .build()
}
package com.springboilerplate.app.hooks

import com.springboilerplate.app.utils.DateTime
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import java.time.LocalDateTime

object LocalDateTimeCoercing : Coercing<LocalDateTime, String> {
        override fun parseValue(input: Any?): LocalDateTime? = DateTime.convertStrTime(serialize(input))

        override fun parseLiteral(input: Any?): LocalDateTime? {
            val dateString = (input as? StringValue)?.value
            return DateTime.convertStrTime(dateString)
        }

        override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult
                .toString()
                .replace('T', ' ')

        val graphqlLocalDateTimeType: GraphQLScalarType = GraphQLScalarType.newScalar()
                .name("LocalDateTime")
                .description("A type representing a formatted java.time.LocalDateTime")
                .coercing(LocalDateTimeCoercing)
                .build()
}
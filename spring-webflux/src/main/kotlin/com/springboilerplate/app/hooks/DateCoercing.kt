package com.springboilerplate.app.hooks

import com.springboilerplate.app.utils.DateTime
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import java.util.Date

object DateCoercing : Coercing<Date, String> {
        override fun parseValue(input: Any?): Date? = DateTime.convertStrTime(input.toString())

        override fun parseLiteral(input: Any?): Date? {
            val dateString = (input as? StringValue)?.value
            return DateTime.convertStrTime(dateString)
        }

        override fun serialize(dataFetcherResult: Any?): String {
            val fetchResult = dataFetcherResult as Date
            return fetchResult
                    .toInstant()
                    .toString()
        }

        val graphqlLocalDateTimeType: GraphQLScalarType = GraphQLScalarType.newScalar()
                .name("Date")
                .description("A type representing a formatted java.util.Date")
                .coercing(DateCoercing)
                .build()
}
package com.springboilerplate.app.hooks

import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import graphql.schema.GraphQLType
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType


class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        UUID::class -> UUIDCoercing.graphqlUUIDType
        LocalDateTime::class -> LocalDateTimeCoercing.graphqlLocalDateTimeType
        else -> null
    }

    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        else -> type
    } ?: type
}

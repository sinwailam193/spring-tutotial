package com.springboilerplate.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.springboilerplate.app.config.App
import com.springboilerplate.app.hooks.CustomDataFetcherFactoryProvider
import com.springboilerplate.app.hooks.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties(App::class)
class SpringBoilerplateApplication {
	@Bean
    fun hooks() = CustomSchemaGeneratorHooks()

	@Bean
    fun dataFetcherFactoryProvider(objectMapper: ObjectMapper) = CustomDataFetcherFactoryProvider(objectMapper)
}

fun main(args: Array<String>) {
	runApplication<SpringBoilerplateApplication>(*args)
}

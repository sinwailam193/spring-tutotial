package com.springboilerplate.app

import com.springboilerplate.app.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class SpringBoilerplateApplication

fun main(args: Array<String>) {
	runApplication<SpringBoilerplateApplication>(*args)
}

package com.springboilerplate.app

import com.springboilerplate.app.config.App
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(App::class)
class SpringBoilerplateApplication

fun main(args: Array<String>) {
	runApplication<SpringBoilerplateApplication>(*args)
}

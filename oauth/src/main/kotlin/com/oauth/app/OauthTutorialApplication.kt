package com.oauth.app

import com.oauth.app.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class OauthTutorialApplication

fun main(args: Array<String>) {
	runApplication<OauthTutorialApplication>(*args)
}

package com.oauth.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OauthTutorialApplication

fun main(args: Array<String>) {
	runApplication<OauthTutorialApplication>(*args)
}

package com.springboilerplate.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class App {
    val auth = Auth()
    var redirectHost: String? = null

    class Auth {
        var tokenSecret: String? = null
        var tokenExpirationMsec: Long = 0
    }
}
package com.oauth.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties {
    val auth = Auth()

    class Auth {
        var tokenSecret: String? = null
        var tokenExpirationMsec: Long = 0
    }
}
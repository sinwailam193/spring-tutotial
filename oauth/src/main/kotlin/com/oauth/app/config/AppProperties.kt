package com.oauth.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties {
    private val auth = Auth()
    private val oauth2 = OAuth2()

    class Auth {
        var tokenSecret: String = ""
        var tokenExpirationMsec: Long = 0
    }

    class OAuth2 {
        var authorizedRedirectUris: List<String> = ArrayList()
            private set

        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
            this.authorizedRedirectUris = authorizedRedirectUris
            return this
        }
    }
}
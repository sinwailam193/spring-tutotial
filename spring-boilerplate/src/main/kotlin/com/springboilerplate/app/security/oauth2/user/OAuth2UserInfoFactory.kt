package com.springboilerplate.app.security.oauth2.user

import com.springboilerplate.app.exception.OAuth2AuthenticationProcessingException
import com.springboilerplate.app.models.AuthProvider

object OAuth2UserInfoFactory {
    @Throws(OAuth2AuthenticationProcessingException::class)
    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        if (registrationId == AuthProvider.google.toString()) {
            return GoogleOAuth2UserInfo(attributes)
        }
        throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
    }
}
package com.springboilerplate.app.services

import com.springboilerplate.app.exception.OAuth2AuthenticationProcessingException
import com.springboilerplate.app.models.AuthProvider
import com.springboilerplate.app.models.User
import com.springboilerplate.app.repository.UserRepository
import com.springboilerplate.app.security.UserPrincipal
import com.springboilerplate.app.security.oauth2.user.OAuth2UserInfo
import com.springboilerplate.app.security.oauth2.user.OAuth2UserInfoFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class CustomOAuth2UserService : DefaultOAuth2UserService() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oauthUser = super.loadUser(userRequest)
        try {
            return processOAuth2User(userRequest, oauthUser)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    @Throws(OAuth2AuthenticationProcessingException::class)
    private fun processOAuth2User(userRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.clientRegistration.registrationId,
                oAuth2User.attributes
        )
        if (StringUtils.isEmpty(userInfo.email)) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }
        var user = userRepository.findByEmail(userInfo.email)
        user = if (user == null) {
            registerNewUser(userRequest, userInfo)
        } else {
            if (user.provider != AuthProvider.valueOf(userRequest.clientRegistration.registrationId)) {
                throw OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with ${user.provider} account. Please use your ${user.provider} account to login."
                )
            }
            updateExistingUser(user, userInfo)
        }
        return UserPrincipal.create(user, oAuth2User.attributes)
    }

    private fun updateExistingUser(exisitingUser: User, oAuth2User: OAuth2UserInfo): User {
        exisitingUser.name = oAuth2User.name
        exisitingUser.picture = oAuth2User.picture
        return exisitingUser
    }

    private fun registerNewUser(userRequest: OAuth2UserRequest, oAuth2User: OAuth2UserInfo): User {
        val user = User(
                name = oAuth2User.name,
                email = oAuth2User.email,
                picture = oAuth2User.picture,
                provider = AuthProvider.valueOf(
                        userRequest.clientRegistration.registrationId
                )
        )
        return userRepository.save(user)
    }
}
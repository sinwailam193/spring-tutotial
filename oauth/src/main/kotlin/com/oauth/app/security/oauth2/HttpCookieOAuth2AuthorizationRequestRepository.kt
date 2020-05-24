package com.oauth.app.security.oauth2

import com.oauth.app.utils.CookieUtils
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpCookieOAuth2AuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    private val AUTHORIZATION_COOKIE_NAME = "authorization"
    private val cookieExpireSeconds = 180

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest = CookieUtils
            .getCookie(request, AUTHORIZATION_COOKIE_NAME)
            .map { CookieUtils.deserialize(it, OAuth2AuthorizationRequest::class.java) }
            .orElse(null)

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest = loadAuthorizationRequest(request)

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest?, request: HttpServletRequest, response: HttpServletResponse) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, AUTHORIZATION_COOKIE_NAME)
            return
        }
        CookieUtils.addCookie(
                response,
                AUTHORIZATION_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest),
                cookieExpireSeconds
        )
//        TODO("use Referer to be the redirect url and add it to the cookie")
        CookieUtils.addCookie(response, "r", "/restricted", cookieExpireSeconds);
    }
}
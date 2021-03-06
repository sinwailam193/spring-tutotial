package com.springboilerplate.app.security.oauth2

import com.springboilerplate.app.config.App
import com.springboilerplate.app.security.TokenProvider
import com.springboilerplate.app.utils.CookieUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler @Autowired internal constructor(
        private val tokenProvider: TokenProvider,
        private val app: App,
        private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationSuccessHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val targetUrl = determineTargetUrl(request, response, authentication)
        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, "${app.redirectHost}$targetUrl")
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    override fun determineTargetUrl(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): String {
        val redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_COOKIE_NAME)
                .map { it.value }
                .orElse(defaultTargetUrl)
        CookieUtils.addCookie(
                response,
                HttpCookieOAuth2AuthorizationRequestRepository.JWT_COOKIE_NAME,
                tokenProvider.createToken(authentication),
                convertMsecToSec()
        )
        return redirectUri
    }

    private fun convertMsecToSec(): Int = TimeUnit.MILLISECONDS.toSeconds(app.auth.tokenExpirationMsec).toInt()
}
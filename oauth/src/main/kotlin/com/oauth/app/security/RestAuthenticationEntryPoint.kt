package com.oauth.app.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*
This class is invoked when a user tries to access a protected resource without authentication.
In this case, we simply return a 401 Unauthorized response
 */

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val logError = LoggerFactory.getLogger(RestAuthenticationEntryPoint::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authException: AuthenticationException
    ) {
        logError.error("Responding with unauthorized error. Message - ${authException.message}")
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                authException.localizedMessage
        )
    }
}

package com.springboilerplate.app.security

import com.springboilerplate.app.exception.ResourceNotFoundException
import com.springboilerplate.app.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import com.springboilerplate.app.utils.CookieUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private  lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    @Autowired
    private lateinit var tokenProvider: TokenProvider

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    private val logError = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val jwt = getJwtFromRequest(request)
        if (jwt != null && StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            val userId = tokenProvider.getUserIdFromToken(jwt)
            val userDetails: UserDetails
            try {
                userDetails = customUserDetailsService.loadUserById(userId)
            } catch (ex: ResourceNotFoundException) {
                httpCookieOAuth2AuthorizationRequestRepository.removeNotValidUserCookie(request, response)
                throw ex
            }
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            logError.error("Could not set user authentication in security context");
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
//        val bearerToken = request.getHeader(HttpCookieOAuth2AuthorizationRequestRepository.JWT_TOKEN_NAME)
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length);
//        }
//        return null
        return CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.JWT_COOKIE_NAME)
                .map { it.value }
                .orElse(null)
    }
}
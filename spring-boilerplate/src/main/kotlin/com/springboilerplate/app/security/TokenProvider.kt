package com.springboilerplate.app.security

import com.springboilerplate.app.config.AppProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenProvider @Autowired internal constructor(private val appProperties: AppProperties) {
    private val logError = LoggerFactory.getLogger(TokenProvider::class.java)

    fun createToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal

        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)

        return Jwts.builder()
                .setSubject(userPrincipal.user.id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.auth.tokenSecret)))
                .compact()
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parserBuilder()
                .setSigningKey(appProperties.auth.tokenSecret)
                .build()
                .parseClaimsJws(token)
                .body
        return claims.subject.toLong()
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(appProperties.auth.tokenSecret)
                    .build()
                    .parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            logError.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logError.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logError.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logError.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logError.error("JWT claims string is empty.")
        }
        return false
    }
}
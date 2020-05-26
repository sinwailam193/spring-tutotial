package com.springboilerplate.app.security

import com.springboilerplate.app.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrincipal(
        val user: User,
        private val authorities: Collection<GrantedAuthority>
) : OAuth2User, UserDetails {
    private var attributes: Map<String, Any>? = null

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return user.email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getAttributes(): Map<String, Any>? {
        return attributes
    }


    override fun getName(): String {
        return user.name!!
    }

    companion object {
        fun create (user: User): UserPrincipal {
            return UserPrincipal(
                    user,
                    listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        }

        fun create(user: User, attributes: Map<String, Any>): UserPrincipal {
            val userPrincipal = create(user)
            userPrincipal.attributes = attributes
            return userPrincipal
        }
    }

}
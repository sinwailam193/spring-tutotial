package com.springboilerplate.app.services

import com.springboilerplate.app.exception.ResourceNotFoundException
import com.springboilerplate.app.repository.UserRepository
import com.springboilerplate.app.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User not found with email : $email")
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id)
                .orElseThrow{ ResourceNotFoundException("User", "id", id) }
        return UserPrincipal.create(user)
    }
}
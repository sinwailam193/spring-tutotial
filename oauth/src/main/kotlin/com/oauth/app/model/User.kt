package com.oauth.app.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(nullable = false)
    private val name: String? = null

    @Column(nullable = false)
    private val email: @Email String? = null

    private val imageUrl: String? = null

    @Column(nullable = false)
    private val emailVerified = false

    @JsonIgnore
    private val password: String? = null

    @Enumerated(EnumType.STRING)
    private val provider: @NotNull AuthProvider? = null

    private val providerId: String? = null
}
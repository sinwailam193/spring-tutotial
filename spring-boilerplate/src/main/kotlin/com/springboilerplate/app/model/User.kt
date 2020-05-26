package com.springboilerplate.app.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users", uniqueConstraints = [
    UniqueConstraint(columnNames = ["email"])
])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @Column(nullable = false)
    var name: String? = null,

    @Email
    @Column(nullable = false)
    var email: String? = null,

    @NotNull
    var picture: String? = null,

    @NotNull
    @Enumerated(EnumType.STRING)
    var provider: AuthProvider? = null
) : Base()
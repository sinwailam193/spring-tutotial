package com.springboilerplate.app.models

import java.util.Date
import javax.persistence.*

@MappedSuperclass
open class Base {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    var createdAt: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    var deletedAt: Date? = null

    @PrePersist
    protected fun onCreate() {
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedAt = Date()
    }
}
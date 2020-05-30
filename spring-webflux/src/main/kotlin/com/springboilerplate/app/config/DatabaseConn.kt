package com.springboilerplate.app.config

import org.davidmoten.rx.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DatabaseConn {
    @Autowired
    lateinit var app: App

    @Bean
    fun provideDatabaseConn(): Database = Database.from(
            app.databaseUrl!!,
            Runtime.getRuntime().availableProcessors() * 2
    )
}
package com.springsecurity.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.springsecurity.app.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConf extends WebSecurityConfigurerAdapter {
    // The password encoder will be the BCryptPasswordEncoder from PasswordConf.java
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConf(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * We want to authorize requests. Any requests must be authenticated. We will
         * use basic authentication to do that. antMatchers allows us to whitelist some
         * path and permit them all
         */
        http.csrf().disable().authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()).anyRequest().authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // this is how you retrieve your user from the database
        UserDetails aaronUser = User.builder().username("aaronlam").password(passwordEncoder.encode("password"))
                .roles(STUDENT.name()) // ROLE_STUDENT
                .build();

        UserDetails lindaUser = User.builder().username("linda").password(passwordEncoder.encode("password123"))
                .roles(ADMIN.name()) // ROLE_ADMIN
                .build();

        UserDetails tomUser = User.builder().username("tom").password(passwordEncoder.encode("password123"))
                .roles(ADMINTRAINEE.name()) // ROLE_ADMIN_TRAINEE
                .build();
        return new InMemoryUserDetailsManager(aaronUser, lindaUser, tomUser);
    }
}
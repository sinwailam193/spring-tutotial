package com.springboilerplate.app.config

import com.springboilerplate.app.security.RestAuthenticationEntryPoint
import com.springboilerplate.app.security.TokenAuthenticationFilter
import com.springboilerplate.app.services.CustomOAuth2UserService
import com.springboilerplate.app.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import com.springboilerplate.app.security.oauth2.OAuth2AuthenticationFailureHandler
import com.springboilerplate.app.security.oauth2.OAuth2AuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    @Autowired
    private lateinit var oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler

    @Autowired
    private lateinit var oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler

    @Bean
    fun cookieAuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository = HttpCookieOAuth2AuthorizationRequestRepository()

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter = TokenAuthenticationFilter()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers(
                        "/",
                        "/**/favicon.ico",
                        "/**/*.svg",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.json"
                    )
                        .permitAll()
                    .antMatchers("/oauth2/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    .successHandler(oAuth2AuthenticationSuccessHandler)

        // Add our custom Token based authentication filter
        http
                .addFilterBefore(
                        tokenAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
    }
}
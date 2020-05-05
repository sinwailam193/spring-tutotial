package com.springsecurity.app.security;

import com.springsecurity.app.auth.ApplicationUserService;
import com.springsecurity.app.jwt.JwtConfig;
import com.springsecurity.app.jwt.JwtTokenVerifier;
import com.springsecurity.app.jwt.JwtUsernamePasswordAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // this is so that we can use @PreAuthorize in @RestController
public class ApplicationSecurityConf extends WebSecurityConfigurerAdapter {
    // The password encoder will be the BCryptPasswordEncoder from PasswordConf.java
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig config;

    @Autowired
    public ApplicationSecurityConf(JwtConfig config, PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.config = config;
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * We want to authorize requests. Any requests must be authenticated. We will
         * use basic authentication to do that. antMatchers allows us to whitelist some
         * path and permit them all
         */
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), config))
            .addFilterAfter(new JwtTokenVerifier(config), JwtUsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            // .antMatchers("/api/**").hasRole(STUDENT.name()) === @PreAuthorize("hasRole(\"ROLE_STUDENT\")")
            // // anyone that has the permission to write can use DELETE /management/api
            // .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) === @PreAuthorize("hasAuthority(\"student:write\")")
            // // anyone that has the permission to write can use POST /management/api
            // .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
            // // anyone that has the permission to write can use PUT /management/api
            // .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
            // // anyone that has the role of ADMIN or ADMINTRAINEE can use GET /management/api
            // .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) === @PreAuthorize("hasAnyRole(\"ROLE_ADMIN\", \"ROLE_ADMINTRAINEE\")")
            .anyRequest()
            .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
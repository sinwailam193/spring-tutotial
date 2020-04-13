package com.springsecurity.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true) // this is so that we can use @PreAuthorize in @RestController
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
        http
            //.csrf().disable()
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
            .authenticated()
            .and()
            .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // this is how you retrieve your user from the database
        UserDetails aaronUser = User.builder()
            .username("aaronlam")
            .password(passwordEncoder.encode("password"))
            .authorities(STUDENT.getGrantedAuthorities())
            .build();

        UserDetails lindaUser = User.builder()
            .username("linda")
            .password(passwordEncoder.encode("password123"))
            .authorities(ADMIN.getGrantedAuthorities())
            .build();

        UserDetails tomUser = User.builder()
            .username("tom")
            .password(passwordEncoder.encode("password123"))
            .authorities(ADMINTRAINEE.getGrantedAuthorities())
            .build();
        return new InMemoryUserDetailsManager(aaronUser, lindaUser, tomUser);
    }
}
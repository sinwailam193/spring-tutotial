package com.springboilerplate.app.controller

import com.springboilerplate.app.security.CurrentUser
import com.springboilerplate.app.security.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restricted")
class RestrictedController {
    @GetMapping
    @PreAuthorize("hasRole(\"USER\")")
    fun getString(@CurrentUser userPrincipal: UserPrincipal): String {
        return "this url path is restricted"
    }
}
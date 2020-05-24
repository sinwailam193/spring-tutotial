package com.oauth.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restricted")
class RestrictedController {
    @GetMapping
    fun getString(): String {
        return "this url path is restricted"
    }
}
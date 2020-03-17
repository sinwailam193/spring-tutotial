package com.mvc.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// adding thie RequestMapping to controller level will add /hello in front
@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }

    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }

    @RequestMapping("/processFormV2")
    public String letsShoutDude(HttpServletRequest request, Model model) {
        String theName = request.getParameter("studentName");
        theName = theName.toUpperCase();
        String res = "Hello! " + theName;
        model.addAttribute("message", res);
        return "helloworld";
    }

    // This request mapping here uses the @RequestParam annotation
    @RequestMapping("/processFormV3")
    public String letsShoutDude(@RequestParam("studentName") String theName, Model model) {
        String res = "Hello! " + theName.toUpperCase();
        model.addAttribute("message", res);
        return "helloworld";
    }
}
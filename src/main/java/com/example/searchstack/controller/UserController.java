package com.example.searchstack.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/login")
    public String login() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signip() {
        return "signup";
    }


}

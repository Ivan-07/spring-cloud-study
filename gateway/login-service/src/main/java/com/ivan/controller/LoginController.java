package com.ivan.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LoginController {

    @GetMapping("login")
    public String login() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}

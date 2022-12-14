package com.ivan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("doOrder")
    public String doOrder() {
        return "来一份半岛咖啡";
    }
}

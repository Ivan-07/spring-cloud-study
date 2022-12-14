package com.ivan.controller;

import com.ivan.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserOrderFeign userOrderFeign;

    @GetMapping("userDoOrder")
    public String userDoOrder() {
        System.out.println("有用户进来了");
        return userOrderFeign.doOrder();
    }

}

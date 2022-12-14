package com.ivan.controller;

import com.ivan.feign.CustomerRentFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CustomerController {

    @Resource
    private CustomerRentFeign customerRentFeign;

    @GetMapping("rent")
    public String rent() {
        System.out.println("客户来租车了");
        return customerRentFeign.rent();
    }
}

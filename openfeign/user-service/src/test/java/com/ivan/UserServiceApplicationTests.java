package com.ivan;

import com.ivan.controller.UserController;
import com.ivan.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
class UserServiceApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

}

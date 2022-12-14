package com.ivan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("testRibbon")
    public String testRibbon(String serviceName) {
        // 正常来说需要拿到ip+port以及路径才能用，利用Ribbon就不需要了
        String result = restTemplate.getForObject("http://" + serviceName + "/hello", String.class);
        return result;
    }
}

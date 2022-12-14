package com.ivan.fish.controller;

import com.ivan.fish.anno.FishAnno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class FishController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("doRpc")
    @FishAnno
    public String doRpc() {
        String result = restTemplate.getForObject("http://localhost:8081/abc", String.class);
        return result;
    }
}

package com.ivan.feign.hystrix;

import com.ivan.feign.CustomerRentFeign;
import org.springframework.stereotype.Component;

@Component
public class CustomerRentFeignHystrix implements CustomerRentFeign {
    @Override
    public String rent() {
        return "我是备胎";
    }
}

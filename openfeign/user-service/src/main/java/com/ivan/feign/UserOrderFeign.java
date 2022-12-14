package com.ivan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order-service")
public interface UserOrderFeign {

    @GetMapping("doOrder")
    String doOrder();
}

package com.ivan.feign.hystrix;

import com.ivan.domain.Order;
import com.ivan.feign.UserOrderFeign;
import org.springframework.stereotype.Component;

@Component
public class UserOrderFeignHystrix implements UserOrderFeign {
    @Override
    public Order getOrderByUserId(Integer userId) {
        return null;
    }
}

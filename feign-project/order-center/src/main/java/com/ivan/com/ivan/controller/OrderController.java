package com.ivan.com.ivan.controller;

import com.ivan.domain.Order;
import com.ivan.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements UserOrderFeign {

    @Override
    public Order getOrderByUserId(Integer userId) {
        Order order = Order.builder().name("鱼香肉丝").price(20D).orderId(1).build();
        return order;
    }
}

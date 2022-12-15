package com.ivan.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    /**
     * 网关的并发比较高 不能在网关里直接操作mysql
     * 可以查redis或者在内存中写好
     */
    public static final List<String> BLACK_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String hostString = request.getHeaders().getHost().getHostString();
        // 查询数据库 看这个ip是否存在于黑名单里
        if (!BLACK_LIST.contains(hostString)) {
            return chain.filter(exchange);
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type","application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 438);
        map.put("msg", "你是黑名单");
        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}

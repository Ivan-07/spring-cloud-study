package com.ivan.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器链模式
     * 责任链模式
     * 网关里面有使用， mybatis的二级缓存有变种责任链模式
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 针对请求的过滤 拿到请求、header、url等
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        System.out.println(path);
        String hostName = request.getRemoteAddress().getHostName();
        System.out.println(hostName);
        String name = request.getMethod().name();
        System.out.println(name);

        return chain.filter(exchange);
    }

    /**
     * 指定顺序的方法
     * 越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

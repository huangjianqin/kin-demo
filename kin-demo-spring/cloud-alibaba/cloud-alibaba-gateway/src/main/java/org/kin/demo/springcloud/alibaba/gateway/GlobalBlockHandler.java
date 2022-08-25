package org.kin.demo.springcloud.alibaba.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author huangjianqin
 * @date 2022/8/25
 */
public final class GlobalBlockHandler implements BlockRequestHandler {
    public static final BlockRequestHandler INSTANCE = new GlobalBlockHandler();

    private GlobalBlockHandler() {
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        System.err.println(throwable);
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just("gateway block"), String.class);
    }
}

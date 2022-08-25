package org.kin.demo.springcloud.alibaba.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@FeignClient(value = "alibaba-provider", fallback = HelloServiceFallback.class)
public interface HelloService {
    @GetMapping(value = "/hello")
    String hello();

    @GetMapping(value = "/config")
    String config();
}

package org.kin.demo.springcloud.nacos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@FeignClient(value = "helloservice", fallback = HelloServiceFallback.class)
public interface HelloService {
    @GetMapping(value = "/hello")
    String hello();
}

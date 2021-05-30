package org.kin.demo.springcloud.zookeeper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huangjianqin
 * @date 2020-07-30
 */
@FeignClient(value = "hello-service")
public interface HelloServiceClient {
    @GetMapping(value = "/hello")
    String hello();
}

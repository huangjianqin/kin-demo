package org.kin.demo.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@FeignClient(value = "HELLOSERVICE", fallback = FeignServiceFallback.class)
public interface FeignService {
    @GetMapping(value = "/hello")
    String hello();

    @GetMapping(value = "/hello1")
        //value必须加上
    String hello1(@RequestParam(value = "name") String name);
}

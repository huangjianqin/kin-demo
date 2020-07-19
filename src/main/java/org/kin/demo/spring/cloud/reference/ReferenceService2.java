package org.kin.demo.spring.cloud.reference;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@FeignClient(value = "KIN-SERVICE1", fallback = ReferenceServiceFallback2.class)
public interface ReferenceService2 {
    @GetMapping(value = "/hello")
    String hello();

    @GetMapping(value = "/hello1")
        //value必须加上
    String hello1(@RequestParam(value = "name") String name);

    @GetMapping(value = "/helloHystrix")
    String helloHystrix();
}

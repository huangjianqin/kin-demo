package org.kin.demo.spring.cloud.feign;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@RestController
public class FeignConsumerController {
    @Resource
    private FeignService feignService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return feignService.hello1("kin");
    }

    @RequestMapping(value = "/helloHystrix", method = RequestMethod.GET)
    public String helloHystrix() {
        return feignService.helloHystrix();
    }
}

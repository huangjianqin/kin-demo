package org.kin.demo.springcloud.alibaba.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@RestController
public class HelloServiceController {
    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        System.out.println("请求hello");
        return helloService.hello();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String config() {
        System.out.println("请求config");
        return helloService.config();
    }
}

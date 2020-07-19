package org.kin.demo.spring.cloud.reference;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author huangjianqin
 * @date 2020-07-17
 */
@RestController
public class ReferenceController {
    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value = "/ribbonHello", method = RequestMethod.GET)
    public String hello(){
        return restTemplate.getForEntity("http://KIN-SERVICE1/hello", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/ribbonHelloHystrix", method = RequestMethod.GET)
    public String helloHystrix() {
        return restTemplate.getForEntity("http://KIN-SERVICE1/helloHystrix", String.class).getBody();
    }

    public String fallback() {
        return "error";
    }
}

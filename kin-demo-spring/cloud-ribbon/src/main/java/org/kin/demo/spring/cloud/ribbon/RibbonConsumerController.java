package org.kin.demo.spring.cloud.ribbon;

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
public class RibbonConsumerController {
    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return restTemplate.getForEntity("http://HELLOSERVICE/hello", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/helloHystrix", method = RequestMethod.GET)
    public String helloHystrix() {
        return restTemplate.getForEntity("http://HELLOSERVICEF/helloHystrix", String.class).getBody();
    }

    public String fallback() {
        return "error";
    }
}

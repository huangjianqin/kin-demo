package org.kin.demo.spring.cloud.reference;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return restTemplate.getForEntity("http://KIN-DEMO/hello", String.class).getBody();
    }
}

package org.kin.demo.spring.cloud.reference;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@RestController
public class ReferenceController2 {
    @Resource
    private ReferenceService2 referenceService2;

    @RequestMapping(value = "/feignHello", method = RequestMethod.GET)
    public String hello() {
        return referenceService2.hello1("kin");
    }

    @RequestMapping(value = "/feignHelloHystrix", method = RequestMethod.GET)
    public String helloHystrix() {
        return referenceService2.helloHystrix();
    }
}

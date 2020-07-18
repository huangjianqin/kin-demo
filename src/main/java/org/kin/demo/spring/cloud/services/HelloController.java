package org.kin.demo.spring.cloud.services;

import org.kin.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huangjianqin
 * @date 2020-07-17
 */
@RestController
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return StringUtils.mkString(discoveryClient.getServices());
    }

    @RequestMapping(value = "/helloHystrix", method = RequestMethod.GET)
    public String helloHystrix() throws InterruptedException {
        int sleepTime = ThreadLocalRandom.current().nextInt(3000);
        Thread.sleep(sleepTime);

        StringBuffer sb = new StringBuffer();
        sb.append(hello()).append(System.lineSeparator())
                .append(sleepTime);

        return sb.toString();
    }
}

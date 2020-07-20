package org.kin.demo.spring.cloud.services;

import org.kin.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huangjianqin
 * @date 2020-07-17
 */
@RefreshScope
@RestController
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${a}")
    private String b;
    @Value("${b}")
    private String d;

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

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1(@RequestParam String name) {
        return name + "---" + StringUtils.mkString(discoveryClient.getServices());
    }

    @GetMapping(value = "/config")
    public String config() {
        return b + d;
    }
}

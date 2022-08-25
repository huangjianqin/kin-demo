package org.kin.demo.springcloud.alibaba.provider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.kin.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @Autowired
    private Environment environment;

    @Value("${a}")
    private int a;
    @Value("${b}")
    private int b;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @SentinelResource(value = "/hello", blockHandler = "helloBlockHandler")
    public String hello() {
        return StringUtils.mkString(discoveryClient.getServices());
    }

    public String helloBlockHandler(BlockException e) {
        return "hello block";
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String config() {
        return a + "-" + b;
    }
}

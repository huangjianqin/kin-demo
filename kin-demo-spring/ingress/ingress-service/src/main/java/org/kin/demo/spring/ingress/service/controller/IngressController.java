package org.kin.demo.spring.ingress.service.controller;

import org.kin.framework.utils.NetUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huangjianqin
 * @date 2023/12/31
 */
@RestController
@RequestMapping("/service")
public class IngressController {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private int port;
    @Value("${HOST_IP:UNKNOWN}")
    private String hostIp;

    @GetMapping("/app")
    public String app() {
        return appName + "_" + port;
    }

    @GetMapping("/random")
    public Integer random() {
        return ThreadLocalRandom.current().nextInt();
    }

    @GetMapping("/hostIp")
    public String hostIp() {
        return hostIp;
    }

    @GetMapping("/localhost")
    public String localhost() {
        return NetUtils.getLocalAddressIp();
    }
}

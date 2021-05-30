package org.kin.demo.springcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableConfigServer
@SpringCloudApplication
public class ConfigCenter {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter.class, args);
    }
}

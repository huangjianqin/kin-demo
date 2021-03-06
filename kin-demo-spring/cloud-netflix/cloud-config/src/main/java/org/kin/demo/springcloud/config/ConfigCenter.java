package org.kin.demo.springcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigCenter {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter.class, args);
    }
}

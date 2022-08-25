package org.kin.demo.springcloud.alibaba.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangjianqin
 * @date 2020-07-28
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        GatewayCallbackManager.setBlockHandler(GlobalBlockHandler.INSTANCE);
        SpringApplication.run(GatewayApplication.class, args);
    }
}

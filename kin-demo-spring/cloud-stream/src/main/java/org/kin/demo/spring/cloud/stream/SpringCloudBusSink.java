package org.kin.demo.spring.cloud.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义Stream input
 *
 * @author huangjianqin
 * @date 2020-07-24
 */
public interface SpringCloudBusSink {
    String INPUT = "springcloudbus";

    @Input(SpringCloudBusSink.INPUT)
    SubscribableChannel input();
}

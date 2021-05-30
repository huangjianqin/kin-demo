package org.kin.demo.springcloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author huangjianqin
 * @date 2020-07-24
 */
public interface SpringCloudStreamSource {
    String OUTPUT = "springcloudbus";

    @Output(SpringCloudStreamSource.OUTPUT)
    MessageChannel output();
}

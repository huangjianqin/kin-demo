package org.kin.demo.spring.cloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

/**
 * @author huangjianqin
 * @date 2020-07-24
 */
@EnableBinding(value = {SpringCloudBusSink.class, SpringCloudStreamSource.class})
@SpringBootApplication
public class KafkaStreamDemo {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamDemo.class, args);
    }

    @StreamListener(SpringCloudBusSink.INPUT)
    public void receive(User user) {
        System.out.println(user);
    }

    @InboundChannelAdapter(value = SpringCloudStreamSource.OUTPUT, poller = @Poller(fixedDelay = "2000"))
    public User create() {
        return User.of("n", "test");
    }
}

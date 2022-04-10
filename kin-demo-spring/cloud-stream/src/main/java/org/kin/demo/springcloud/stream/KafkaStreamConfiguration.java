package org.kin.demo.springcloud.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author huangjianqin
 * @date 2022/4/10
 */
@Configuration
public class KafkaStreamConfiguration {
    @Bean
    public Consumer<User> input() {
        return System.out::println;
    }

    @InboundChannelAdapter(value = "output", poller = @Poller(fixedDelay = "2000"))
    @Bean
    public Supplier<User> output() {
        return () -> User.of("n", "test");
    }
}

package org.kin.demo.spring.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.kin.demo.spring.kafka.common.C1;
import org.kin.demo.spring.kafka.common.C2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangjianqin
 * @date 2020/11/27
 */
@SpringBootApplication
public class KafkaDemo {
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemo.class).close();
    }

    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        return new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    /**
     * 若配置converter, 则按converter后的类型进行方法匹配
     */
    @Bean
    public RecordMessageConverter converter() {
        StringJsonMessageConverter converter = new StringJsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages(C1.class.getPackage().getName());
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("c1", C1.class);
        mappings.put("c2", C2.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public NewTopic c1() {
        return new NewTopic("c1", 1, (short) 1);
    }

    @Bean
    public NewTopic c2() {
        return new NewTopic("c2", 1, (short) 1);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("Hit Enter to terminate...");
            System.in.read();
        };
    }

    /**
     * 读取原生格式
     */
    @KafkaListener(id = "singleGroup1", topics = "c1")
    public void consumer1(String c1) {
        System.out.println("Received source message from c1: " + c1);
    }

    /**
     * 读取原生格式
     */
    @KafkaListener(id = "singleGroup2", topics = "c2")
    public void consumer2(String c2) {
        System.out.println("Received source message from c2: " + c2);
    }
}

package org.kin.demo.spring.kafka;

import org.kin.demo.spring.kafka.common.C1;
import org.kin.demo.spring.kafka.common.C2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author huangjianqin
 * @date 2020/11/27
 */
@Component
@KafkaListener(id = "multiGroup", topics = {"c1", "c2"})
public class MultiMethods {
    @KafkaHandler
    public void c1(C1 c1) {
        System.out.println("Received: " + c1);
    }

    @KafkaHandler
    public void c2(C2 c2) {
        System.out.println("Received: " + c2);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}

package org.kin.demo.spring.kafka;

import org.kin.demo.spring.kafka.common.C1;
import org.kin.demo.spring.kafka.common.C2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjianqin
 * @date 2020/11/27
 */
@RestController
public class Controller {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/c1/{what}")
    public void sendC1(@PathVariable String what) {
        this.template.send("c1", C1.of(Integer.parseInt(what)));
    }

    @PostMapping(path = "/c2/{what}")
    public void sendC2(@PathVariable String what) {
        this.template.send("c2", C2.of(what));
    }

    @PostMapping(path = "/un/{what}")
    public void sendUnknown(@PathVariable String what) {
        this.template.send("c2", what);
    }

}

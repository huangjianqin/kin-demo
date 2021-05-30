package org.kin.demo.springcloud.nacos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@RestController
public class NacosFeignController {
    @Resource
    private HelloService helloService;

//    @PostConstruct
//    public void init(){
//        String res = "GET:http://helloservice/config";
//
//        FlowRule rule1 = new FlowRule();
//        rule1.setCount(1);
//        rule1.setResource(res);
//        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule1.setLimitApp("default");
//
//        FlowRuleManager.loadRules(Collections.singletonList(rule1));
//
//        DegradeRule rule2 = new DegradeRule();
//        rule2.setResource(res);
//        rule2.setCount(1);
//        rule2.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//        rule2.setTimeWindow(1);
//        rule2.setMinRequestAmount(1);
//
//        DegradeRuleManager.loadRules(Collections.singletonList(rule2));
//    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String config() {
        return helloService.config();
    }
}

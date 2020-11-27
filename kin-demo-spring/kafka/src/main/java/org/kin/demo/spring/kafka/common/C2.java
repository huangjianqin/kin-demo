package org.kin.demo.spring.kafka.common;

import java.io.Serializable;

/**
 * @author huangjianqin
 * @date 2020/11/27
 */
public class C2 implements Serializable {
    private static final long serialVersionUID = -8388415727755257499L;
    private String str;

    public static C2 of(String str) {
        C2 inst = new C2();
        inst.str = str;
        return inst;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "C2{" +
                "str='" + str + '\'' +
                '}';
    }
}

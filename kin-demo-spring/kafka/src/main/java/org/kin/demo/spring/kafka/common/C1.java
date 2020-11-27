package org.kin.demo.spring.kafka.common;

import java.io.Serializable;

/**
 * @author huangjianqin
 * @date 2020/11/27
 */
public class C1 implements Serializable {
    private static final long serialVersionUID = 2675745982663049261L;
    private int number;

    public static C1 of(int number) {
        C1 inst = new C1();
        inst.number = number;
        return inst;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "C1{" +
                "number=" + number +
                '}';
    }
}

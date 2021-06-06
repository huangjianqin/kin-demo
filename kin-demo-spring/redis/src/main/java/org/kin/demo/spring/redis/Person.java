package org.kin.demo.spring.redis;

import java.io.Serializable;

/**
 * @author huangjianqin
 * @date 2021/6/5
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 2296435041706887781L;

    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //setter && getter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}

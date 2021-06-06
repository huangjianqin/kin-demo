package org.kin.demo.spring.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author huangjianqin
 * @date 2021/6/6
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 2296435041706887781L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //setter && getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

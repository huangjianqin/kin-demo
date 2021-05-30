package org.kin.demo.springcloud.stream;

import java.io.Serializable;

/**
 * @author huangjianqin
 * @date 2020-07-24
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3452999959324160979L;

    private String name;
    private String account;

    public static User of(String name, String account) {
        User user = new User();
        user.name = name;
        user.account = account;
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}

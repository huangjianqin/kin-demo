package org.kin.demo.java.utils;

import am.ik.timeflake.Timeflake;

/**
 * @author huangjianqin
 * @date 2022/3/1
 */
public class TimeflakeTest {
    public static void main(String[] args) {
        Timeflake timeflake = Timeflake.generate();
        System.out.println(timeflake.value());
        System.out.println(timeflake.toUuid());
        System.out.println(timeflake.toInstant());
        System.out.println(timeflake.base62());
    }
}

package com.example.model;

/**
 * @author wxl
 * @date on 2017/12/20.
 * @describe:
 */

public class User {
    public int userId;
    public String name;
    public boolean sex;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}

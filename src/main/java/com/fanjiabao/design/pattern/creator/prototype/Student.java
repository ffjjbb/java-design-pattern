package com.fanjiabao.design.pattern.creator.prototype;

import java.io.Serializable;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 15:19
 * @description: 学生
 */
public class Student implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

}

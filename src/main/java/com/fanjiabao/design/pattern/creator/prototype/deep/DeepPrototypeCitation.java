package com.fanjiabao.design.pattern.creator.prototype.deep;

import com.fanjiabao.design.pattern.creator.prototype.Student;

import java.io.Serializable;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 15:20
 * @description: 具体原型类
 * Cloneable 接口为抽象原型类
 */
public class DeepPrototypeCitation implements Cloneable, Serializable {

    private Student stu;

    public Student getStu() {
        return stu;
    }

    public void setStu(Student stu) {
        this.stu = stu;
    }

    @Override
    public DeepPrototypeCitation clone() throws CloneNotSupportedException {
        return (DeepPrototypeCitation) super.clone();
    }

    public void show() {
        System.out.println(stu.getName() + " 加油");
    }

}

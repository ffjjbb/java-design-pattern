package com.fanjiabao.design.pattern.creator.prototype.shallow;

import com.fanjiabao.design.pattern.creator.prototype.Student;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 15:20
 * @description: 具体原型类
 * Cloneable 接口为抽象原型类
 */
public class ShallowPrototypeCitation implements Cloneable {

    private Student stu;

    public Student getStu() {
        return stu;
    }

    public void setStu(Student stu) {
        this.stu = stu;
    }

    @Override
    public ShallowPrototypeCitation clone() throws CloneNotSupportedException {
        return (ShallowPrototypeCitation) super.clone();
    }

    public void show() {
        System.out.println(stu.getName() + " 同学: 今年业绩优秀, 赏！");
    }

}

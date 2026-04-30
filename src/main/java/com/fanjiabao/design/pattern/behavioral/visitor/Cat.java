package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 11:39
 * @description: 具体元素角色类(宠物猫)
 */
public class Cat implements Animal {

    @Override
    public void accept(Person person) {
        // 访问者给宠物猫喂食
        person.feed(this);
        System.out.println("猫说: 味正");
    }

}

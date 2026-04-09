package com.fanjiabao.design.pattern.creator.factory.simple;

import com.fanjiabao.design.pattern.creator.factory.AmericanCoffee;
import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.LatteCoffee;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/8 11:11
 * @description:
 * 工厂模式.简单工厂模式: 简单工厂不是一种设计模式, 反而比较像是一种编程习惯。
 */
public class SimpleFactory {

    /**
     * 优: 封装了创建对象的过程, 可以通过参数直接获取对象。
     * 缺: 增加新产品时还是需要修改工厂类的代码, 违背 '开闭原则'
     * ---------------------------
     * 简单工厂包含如下角色:
     *  抽象产品: 定义了产品的规范，描述了产品的主要特性和功能
     *  具体产品: 实现或者继承抽象产品的子类
     *  具体工厂: 提供了创建产品的方法，调用者通过该方法来获取产品
     */
    public Coffee createCoffee(String type) {
        Coffee coffee = null;
        if("americano".equals(type)) {
            coffee = new AmericanCoffee();
        } else if("latte".equals(type)) {
            coffee = new LatteCoffee();
        }
        return coffee;
    }
    // 静态工厂模式: 给 createCoffee 加 static 关键字


    public static void main(String[] args) {
        SimpleFactory store = new SimpleFactory();
        Coffee coffee = store.createCoffee("latte");
        System.out.println(coffee.getName());
    }

}

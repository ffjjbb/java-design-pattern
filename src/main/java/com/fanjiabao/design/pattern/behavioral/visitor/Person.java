package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * 抽象访问者角色类
 */
public interface Person {

    // 喂狗
    void feed(Cat cat);

    // 喂猫
    void feed(Dog dog);

}

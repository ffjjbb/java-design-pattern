package com.fanjiabao.design.pattern.behavioral.mediator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 15:51
 * @description: 抽象同事类
 */
public abstract class Person {

    protected String name;

    protected Mediator mediator;

    public Person(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }
}

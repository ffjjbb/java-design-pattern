package com.fanjiabao.design.pattern.creator.singleton.hungry.three;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:17
 * @description:
 * 单例设计模式.饿汉式: 类加载就会导致该单实例对象被创建
 * 枚举方式
 */
public enum HungryPlanThree {

    /**
     * 由 JVM 保证实例唯一，线程安全，并且天然防止反射和反序列化破坏单例，相比传统饿汉式或懒汉式更加安全可靠。
     * 枚举在反序列化时，JVM不会创建新对象，而是通过 Enum.valueOf() 返回已有实例，因此天然保证单例。相关源码: Enum.java:267
     * 如果通过反射创建枚举类, 则报错。相关源码: Constructor.java:492
     */
    INSTANCE;

    public void doSomething() {
        System.out.println("做一些事情");
    }

}

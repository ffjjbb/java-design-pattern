package com.fanjiabao.design.pattern.creator.singleton.hungry.one;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:08
 * @description:
 * 单例设计模式.饿汉式: 类加载就会导致该单实例对象被创建
 * 静态变量方式
 */
public class HungryPlanOne {

    private HungryPlanOne() {}

    private static HungryPlanOne instance = new HungryPlanOne();

    /**
     * 该方式在成员位置声明 HungryPlanOne 类型的静态变量, 并创建 HungryPlanOne 类的对象 instance。
     * instance 对象是随着类的加载而创建的。
     * 如果该对象足够大的话，而一直没有使用就会造成内存的浪费。
     */
    public static HungryPlanOne getInstance() {
        return instance;
    }

}

package com.fanjiabao.design.pattern.creator.singleton.hungry.two;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:08
 * @description:
 * 单例设计模式.饿汉式: 类加载就会导致该单实例对象被创建
 * 静态代码块方式
 */
public class HungryPlanTwo {

    private HungryPlanTwo() {}

    private static HungryPlanTwo instance;

    static {
        instance = new HungryPlanTwo();
    }

    /**
     * 该方式在成员位置声明 HungryPlanTwo 类型的静态变量, 而对象的创建是在静态代码块中, 也是对着类的加载而创建。
     * 所以和 HungryPlanOne 基本上一样, 该方式也存在内存浪费问题。
     */
    public static HungryPlanTwo getInstance() {
        return instance;
    }

}

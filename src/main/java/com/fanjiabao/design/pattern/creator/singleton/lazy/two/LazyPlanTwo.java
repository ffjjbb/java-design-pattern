package com.fanjiabao.design.pattern.creator.singleton.lazy.two;

import java.io.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:17
 * @description:
 * 单例设计模式.懒汉式: 类加载不会导致该单实例对象被创建,而是首次使用该对象时才会创建
 * 静态内部类方式
 */
public class LazyPlanTwo implements Serializable {

    private LazyPlanTwo() {}

    /**
     * JVM 在加载外部类的过程中, 是不会加载静态内部类的, 只有内部类的属性/方法被调用时才会被加载, 并初始化其静态属性。
     * 静态属性由于被 static 修饰，保证只被实例化一次，并且严格保证实例化顺序。
     */
    private static class LazyTwoHolder {
        private static final LazyPlanTwo instance = new LazyPlanTwo();
    }

    /**
     * 静态内部类单例模式是一种优秀的单例模式，是开源项目中比较常用的一种单例模式。
     * 在没有加任何锁的情况下，保证了多线程下的安全，并且没有任何性能影响和空间的浪费。
     */
    public static LazyPlanTwo getInstance() {
        return LazyTwoHolder.instance;
    }


    /**
     * 序列化反序列化破坏单例模式, 需要编写 readResolve() 方法处理
     */
    public static void main(String[] args) throws Exception {
        LazyPlanTwo instance1 = LazyPlanTwo.getInstance();
        // 序列化到文件
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lazyTwo.obj"));
        oos.writeObject(instance1);
        oos.close();

        // 从文件反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lazyTwo.obj"));
        LazyPlanTwo instance2 = (LazyPlanTwo) ois.readObject();
        ois.close();

        // 比较两个对象
        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance1 == instance2);
    }


    /**
     * 在反序列化过程中, ObjectInputStream 会先创建一个新对象, 然后通过反射检查是否存在 readResolve 方法。
     * 如果存在, 则调用该方法, 并用其返回值替换原本反序列化得到的对象, 从而保证返回的是单例实例, 而不是新创建的对象。
     * ==================================
     * 相关源码解析:
     * ois.readObject()
     *  --> readObject(Object.class)
     *      --> readObject0(type, false)
     *          --> readOrdinaryObject(unshared)
     *              ---> Object rep = desc.invokeReadResolve(obj);
     *                  --> if (rep != obj) {...}
     *                      用 readResolve 的返回值替换原对象，同时更新对象句柄表，保证反序列化返回值和引用一致。
     *                      --> handles.setObject(passHandle, obj = rep);
     * ==================================
     * 只要不是枚举，实现 Serializable 的单例都会被反序列化破坏，必须加 readResolve()
     */
    private Object readResolve() {
        return LazyTwoHolder.instance;
    }

}

package com.fanjiabao.design.pattern.creator.singleton.lazy.one;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:17
 * @description:
 * 单例设计模式.懒汉式: 类加载不会导致该单实例对象被创建,而是首次使用该对象时才会创建
 * 双重检查锁
 */
public class LazyPlanOne {

    // JVM 在实例化对象的时候会进行优化和指令重排序操作, 使用 volatile 保证可见性和有序性
    private static volatile LazyPlanOne instance;

    public static LazyPlanOne getInstance() {
        // get方法属于读请求, 无需加锁, 快速响应
        if (instance == null) {
            // 若两个线程等待锁, A线程创建示例后返回, B线程需要再判断一次, 避免重复创建
            synchronized (LazyPlanOne.class) {
                if (instance == null) {
                    instance = new LazyPlanOne();
                }
            }
        }
        return instance;
    }

    /**
     * 反射破坏单例模式, 通过在私有构造器增加访问标识判断是否抛异常
     */
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz = LazyPlanOne.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        // 取消访问检查
        constructor.setAccessible(true);

        LazyPlanOne instance1 = (LazyPlanOne) constructor.newInstance();
        LazyPlanOne instance2 = (LazyPlanOne) constructor.newInstance();

        // 比较两个对象
        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance1 == instance2);
    }

    private static boolean flag = false;

    private LazyPlanOne() {
        synchronized (LazyPlanOne.class) {
            // 通过标识位判断是否已经创建实例, 防止反射重复创建实例
            if (flag) {
                throw new RuntimeException("LazyOne instance has already been created.");
            }
            flag = true;
        }
    }

}

package com.fanjiabao.design.pattern.structural.proxy.jdk_dynamic_prox;

import com.fanjiabao.design.pattern.structural.proxy.SellTickets;
import com.fanjiabao.design.pattern.structural.proxy.TrainStation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/14 11:56
 * @description: 代理工厂, 创建代理对象
 *  JDKProxyFactory 不是代理模式中所说的代理类, 代理类是程序在运行过程中动态的在内存中生成的类
 */
public class JDKProxyFactory {

    private TrainStation station = new TrainStation();

    /**
     * 使用 Proxy 获取代理对象
     */
    public SellTickets getProxyObject() {
        /**
         * newProxyInstance()方法参数说明:
         *  loader ClassLoader loader: 类加载器, 用于加载代理类, 使用真实对象的类加载器即可
         *  Class<?>[] interfaces: 真实对象所实现的接口, 代理模式真实对象和代理对象实现相同的接口
         *  InvocationHandler h: 代理对象的调用处理程序
         */
        return (SellTickets) Proxy
                .newProxyInstance(
                        station.getClass().getClassLoader(),
                        // 面向接口编程设计原则
                        // JDK 动态代理通过 "实现接口" 来生成代理类, 由于 Java 单继承限制, 它无法继承目标类, 因此必须依赖接口来保
                        // 证代理类与目标类具有相同的方法结构
                        station.getClass().getInterfaces(),
                        new InvocationHandler() {
                            /**
                             * 如果接口中有多个方法时, JDK 动态代理会为每个方法生成代理实现, 但所有方法调用最终都会统一转
                             * 发到 InvocationHandler.invoke(), 不关心方法是谁全部增强。 可以通过 Method 参数区分
                             * 具体调用的方法走不同的逻辑。
                             * <p>
                             * InvocationHandler 中 invoke 方法参数说明:
                             *  @param proxy 代理对象
                             *  @param method 对应于在代理对象上调用的接口方法的 Method 实例
                             *  @param args 代理对象调用接口方法时传递的实际参数
                             */
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                System.out.println("代理点收取一些服务费用(JDK动态代理方式)");
                                // 执行真实对象
                                // 代理对象 proxy != 真实对象 station, 如果写 method.invoke(proxy, args); 会陷入死循环
                                Object invoke = method.invoke(station, args);
                                System.out.println("代理点上报交易明细");
                                return invoke;
                            }
                        });
    }

}

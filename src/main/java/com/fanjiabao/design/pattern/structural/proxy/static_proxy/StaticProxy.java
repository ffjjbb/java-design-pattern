package com.fanjiabao.design.pattern.structural.proxy.static_proxy;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:08
 * @description:
 * 结构型模式.代理模式.静态代理:
 *  - 由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时, 访问对象不适合或者不能直接引用目标对象, 代理对象作为访问对象
 *  和目标对象之间的中介。
 *  - Java中的代理按照代理类生成时机不同又分为静态代理和动态代理。静态代理代理类在编译期就生成, 而动态代理代理类则是在Java运行时
 *  动态生成。动态代理又有JDK代理和CGLib代理两种。
 */
public class StaticProxy {

    /**
     * 代理模式分为三种角色:
     *  抽象主题类(Subject): 通过接口或抽象类声明真实主题和代理对象实现的业务方法。
     *  真实主题类(Real Subject): 实现了抽象主题中的具体业务, 是代理对象所代表的真实对象, 是最终要引用的对象。
     *  代理类(Proxy): 提供了与真实主题相同的接口, 其内部含有对真实主题的引用, 它可以访问、控制或扩展真实主题的功能。
     */
    public static void main(String[] args) {
        // ProxyPoint 作为访问对象和目标对象的中介, 同时也对sell方法进行了增强。
        ProxyPoint pp = new ProxyPoint();
        pp.sell();
    }

}

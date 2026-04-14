package com.fanjiabao.design.pattern.structural.proxy.cglib_dynamic_prox;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/14 15:46
 * @description:
 * 结构型模式.代理模式.CGLIB动态代理:
 *  CGLIB 通过 "继承目标类 + 重写方法 + 方法拦截" 实现代理。它为没有实现接口的类提供代理, 为JDK的动态代理
 *  提供了很好地补充, 需要引入 cglib 包。
 * <p>
 *  注: CGLib 原理是动态生成被代理类的子类, 不能对声明为 final 的类或者方法进行代理
 */
public class CGLIBDynamicProx {

    /**
     * 启动类增加: --add-opens java.base/java.lang=ALL-UNNAMED
     * 打开 JDK 核心模块中的 java.lang 包, 允许普通代码通过反射访问其内部成员, 从而绕过 Java 9+ 的模块封装限制。
     * <p>
     * 生成的代理类如: CGLIBTrainStation$$EnhancerByCGLIB$$fe66fc1b.txt
     * <p>
     * 完整的调用链:
     *  1.proxyObject.sell();
     *  2.进入代理类执行 CGLIBTrainStation$$EnhancerByCGLIB$$fe66fc1b.sell()
     *  3.有拦截器调用 MethodInterceptor.intercept() = CGLIBProxyFactory.intercept()
     *  4.前置增强
     *  5.methodProxy.invokeSuper()
     *      CGLIB 提前生成了一个 "方法索引表 + switch调用结构", 绕过了反射，直接定位并调用 super 方法
     *      --> FastClass.invoke(index)
     *          --> 调用 CGLIBTrainStation$$EnhancerByCGLIB$$fe66fc1b.CGLIB$sell$0()
     *              --> super.sell()
     *                  --> 调用父类方法：CGLIBTrainStation.sell()
     *  8.后置增强
     * <p>
     *  查看 CGLIB 生成的 FastClass:
     *  启动类增加参数:
     *      -Dcglib.debugLocation=.\src\main\java\com\fanjiabao\design\pattern\structural\proxy\cglib_dynamic_prox\cglib_debug
     *  查看 FastClassByCGLIB 文件可以看到 switch 调用结构
     * ---------------------------
     * 优点:
     *  - 代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用
     *  - 代理对象可以扩展目标对象的功能
     *  - 代理模式能将客户端与目标对象分离, 在一定程度上降低了系统的耦合度
     * 缺点: 增加了系统的复杂度
     */
    public static void main(String[] args) {
        // 代理工厂对象
        CGLIBProxyFactory factory = new CGLIBProxyFactory();
        // 获取代理对象
        CGLIBTrainStation proxyObject = factory.getProxyObject();
        System.out.println("代理类: " + proxyObject.getClass());
        System.out.println("类加载器: " + proxyObject.getClass().getClassLoader());
        System.out.println("------------------------------------");

        proxyObject.sell();
        try {
            System.in.read();
        } catch (Exception e) {}
    }

}

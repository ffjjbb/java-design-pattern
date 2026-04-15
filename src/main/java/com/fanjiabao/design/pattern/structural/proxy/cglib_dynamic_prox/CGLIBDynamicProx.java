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
     * 生成的代理类如: ./CGLIBTrainStation$$EnhancerByCGLIB$$fe66fc1b.txt
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


    /**
     * 三种代理的对比:
     *  jdk代理 和 CGLIB代理
     *      使用CGLib实现动态代理, CGLib底层采用ASM字节码生成框架, 使用字节码技术生成代理类, 在JDK1.6之前比使用Java反射效率要高。
     *      唯一需要注意的是, CGLib不能对声明为final的类或者方法进行代理, 因为CGLib原理是动态生成被代理类的子类。在JDK1.6、JDK1.7、
     *      JDK1.8逐步对JDK动态代理优化之后，在调用次数较少的情况下，JDK代理效率高于CGLib代理效率，只有当进行大量调用的时候，JDK1.6
     *      和JDK1.7比CGLib代理效率低一点，但是到JDK1.8的时候，JDK代理效率高于CGLib代理。所以如果有接口使用JDK动态代理，如果没有接
     *      口使用CGLIB代理。
     *  动态代理和静态代理
     *      动态代理与静态代理相比较, 最大的好处是接口中声明的所有方法都被转移到调用处理器一个集中的方法中处理(InvocationHandler.invoke)。
     *      这样, 在接口方法数量比较多的时候, 我们可以进行灵活处理, 而不需要像静态代理那样每一个方法进行中转。如果接口增加一个方法，静态代理模
     *      式除了所有实现类需要实现这个方法外, 所有代理类也需要实现此方法。增加了代码维护的复杂度, 而动态代理不会出现该问题。
     * <p>
     * 优点:
     *  - 代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用
     *  - 代理对象可以扩展目标对象的功能
     *  - 代理模式能将客户端与目标对象分离, 在一定程度上降低了系统的耦合度
     * 缺点: 增加了系统的复杂度
     * <p>
     * 使用场景:
     *  远程(Remote)代理
     *      本地服务通过网络请求远程服务。为了实现本地到远程的通信, 我们需要实现网络通信, 处理其中可能的异常。为良好的代码设计和可维护性,
     *      我们将网络通信部分隐藏起来, 只暴露给本地服务一个接口, 通过该接口即可访问远程服务提供的功能, 而不必过多关心通信部分的细节。
     *  防火墙(Firewall)代理
     *      当你将浏览器配置成使用代理功能时, 防火墙就将你的浏览器的请求转给互联网; 当互联网返回响应时, 代理服务器再把它转给你的浏览器。
     *  保护(Protect or Access)代理
     *      控制对一个对象的访问, 如果需要, 可以给不同的用户提供不同级别的使用权限。
     */
    public void usageScenarios() {}

}

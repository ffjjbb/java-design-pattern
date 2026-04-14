package com.fanjiabao.design.pattern.structural.proxy.jdk_dynamic_prox;

import com.fanjiabao.design.pattern.structural.proxy.SellTickets;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/7 12:08
 * @description:
 * 结构型模式.代理模式.JDK动态代理:
 *  Java中提供了一个动态代理类Proxy, Proxy并不是我们上述所说的代理对象的类, 而是提供了一个创建代理对象的静态方法(newProxyInstance方法)来
 *  获取代理对象。
 */
public class JDKDynamicProx {

    /**
     * 使用 Arthas 查看代理类:
     *  - 运行 java -jar arthas-boot.jar --telnet-port 9998 --http-port 9999
     *  - 选择当前类所处进程
     *  - 输入 "jad '代理类'" 查看代理类内容(如 ./$Proxy0.txt)
     *  <p>
     * 完整的调用链:
     *  1.调用代理对象: proxyObject.sell() 实际执行的是: $Proxy0.sell()
     *  2.进入代理类:
     *      public final class $Proxy0 extends Proxy implements SellTickets{
     *          @Override
     *          public final void sell() {
     *              // this = 代理对象, m3 = sell() 方法, h = InvocationHandler, null = args
     *              this.h.invoke(this, m3, null);
     *          }
     *      }
     *  3.进入 InvocationHandler.invoke()
     *      public Object invoke(Object proxy, Method method, Object[] args) {
     *          // [增强逻辑（前置）]
     *          System.out.println("代理点收取一些服务费用(JDK动态代理方式)");
     *          // [调用真实方法]
     *          Object invoke = method.invoke(station, args);
     *          // [增强逻辑（后置）]
     *          System.out.println("代理点上报交易明细");
     *          return invoke;
     *      }
     */
    public static void main(String[] args) {
        // 使 JDK 动态代理生成的 $Proxy 类保存到磁盘, 而不是只存在于内存中
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        JDKProxyFactory factory = new JDKProxyFactory();
        SellTickets proxyObject = factory.getProxyObject();

        System.out.println("代理类: " + proxyObject.getClass());
        System.out.println("类加载器: " + proxyObject.getClass().getClassLoader());
        System.out.println("------------------------------------");

        // JDK 动态代理的本质是用一个代理类拦截所有方法调用, 并统一交给 InvocationHandler.invoke() 来决定如何执行(增强 or 放行)
        proxyObject.sell();
        try {
            System.in.read();
        } catch (Exception e) {}
    }

}

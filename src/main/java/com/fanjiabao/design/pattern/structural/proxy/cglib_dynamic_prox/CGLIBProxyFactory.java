package com.fanjiabao.design.pattern.structural.proxy.cglib_dynamic_prox;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/14 16:04
 * @description: 代理工厂
 */
public class CGLIBProxyFactory implements MethodInterceptor {

    private CGLIBTrainStation target = new CGLIBTrainStation();

    public CGLIBTrainStation getProxyObject() {
        // 创建 Enhancer 对象, 类似于 JDK动态代理的 Proxy 类
        Enhancer enhancer = new Enhancer();
        // 设置父类的字节码对象
        enhancer.setSuperclass(target.getClass());
        // 设置回调函数
        enhancer.setCallback(this);
        // 创建代理对象
        CGLIBTrainStation obj = (CGLIBTrainStation) enhancer.create();
        return obj;
    }


    /**
     * 拦截器代码
     * <p>
     * intercept方法参数说明:
     * @param o 代理对象
     * @param method 真实对象中的方法的 Method 实例
     * @param args 实际参数
     * @param methodProxy 代理对象中的方法的 method 实例
     */
    @Override
    public CGLIBTrainStation intercept(Object o,
                                       Method method,
                                       Object[] args,
                                       MethodProxy methodProxy
    ) throws Throwable {
        // 前置增强
        System.out.println("代理点收取一些服务费用(JDK动态代理方式)");
        // method.invoke 使用反射-慢, methodProxy.invokeSuper 直接调用字节码-快
        CGLIBTrainStation result = (CGLIBTrainStation) methodProxy.invokeSuper(o, args);
        // 后置增强
        System.out.println("代理点上报交易明细");
        return result;
    }

}

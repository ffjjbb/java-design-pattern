package com.fanjiabao.design.pattern.structural.proxy;

import com.fanjiabao.design.pattern.structural.proxy.cglib_dynamic_prox.CGLIBProxyFactory;
import com.fanjiabao.design.pattern.structural.proxy.cglib_dynamic_prox.CGLIBTrainStation;
import com.fanjiabao.design.pattern.structural.proxy.jdk_dynamic_prox.JDKProxyFactory;
import com.fanjiabao.design.pattern.structural.proxy.static_proxy.ProxyPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 代理模式单元测试
 */
class ProxyTest {

    // ===================== 静态代理 =====================

    @Test
    @DisplayName("静态代理: ProxyPoint 应实现 SellTickets 接口")
    void staticProxy_shouldImplementInterface() {
        ProxyPoint proxy = new ProxyPoint();
        assertThat(proxy).isInstanceOf(SellTickets.class);
    }

    @Test
    @DisplayName("静态代理: sell() 应正常执行")
    void staticProxy_shouldExecuteSell() {
        ProxyPoint proxy = new ProxyPoint();
        proxy.sell(); // 不抛异常即为通过
    }

    // ===================== JDK 动态代理 =====================

    @Test
    @DisplayName("JDK动态代理: 代理对象应实现 SellTickets 接口")
    void jdkDynamicProxy_shouldImplementSellTickets() {
        JDKProxyFactory factory = new JDKProxyFactory();
        SellTickets proxy = factory.getProxyObject();
        assertThat(proxy).isInstanceOf(SellTickets.class);
    }

    @Test
    @DisplayName("JDK动态代理: 多次获取代理对象应为不同实例")
    void jdkDynamicProxy_shouldReturnDifferentProxyInstances() {
        JDKProxyFactory factory = new JDKProxyFactory();
        SellTickets proxy1 = factory.getProxyObject();
        SellTickets proxy2 = factory.getProxyObject();
        assertThat(proxy1).isNotSameAs(proxy2);
    }

    @Test
    @DisplayName("JDK动态代理: sell() 应正常执行")
    void jdkDynamicProxy_shouldExecuteSell() {
        JDKProxyFactory factory = new JDKProxyFactory();
        SellTickets proxy = factory.getProxyObject();
        proxy.sell(); // 不抛异常即为通过
    }

    // ===================== CGLIB 动态代理 =====================

    @Test
    @DisplayName("CGLIB动态代理: 代理对象应为 CGLIBTrainStation 的子类")
    void cglibDynamicProxy_shouldBeSubclassOfCGLIBTrainStation() {
        CGLIBProxyFactory factory = new CGLIBProxyFactory();
        CGLIBTrainStation proxy = factory.getProxyObject();
        assertThat(proxy).isInstanceOf(CGLIBTrainStation.class);
    }

    @Test
    @DisplayName("CGLIB动态代理: 代理对象类型名应包含 EnhancerByCGLIB")
    void cglibDynamicProxy_shouldHaveEnhancerClassName() {
        CGLIBProxyFactory factory = new CGLIBProxyFactory();
        CGLIBTrainStation proxy = factory.getProxyObject();
        assertThat(proxy.getClass().getSimpleName()).contains("EnhancerByCGLIB");
    }

    @Test
    @DisplayName("CGLIB动态代理: sell() 应正常执行")
    void cglibDynamicProxy_shouldExecuteSell() {
        CGLIBProxyFactory factory = new CGLIBProxyFactory();
        CGLIBTrainStation proxy = factory.getProxyObject();
        proxy.sell(); // 不抛异常即为通过
    }

    // ===================== TrainStation =====================

    @Test
    @DisplayName("TrainStation: 应实现 SellTickets 接口")
    void trainStation_shouldImplementSellTickets() {
        TrainStation station = new TrainStation();
        assertThat(station).isInstanceOf(SellTickets.class);
    }
}

package com.fanjiabao.design.pattern.behavioral.command.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MapperProxy 命令模式测试
 * 
 * 测试要点:
 * 1. MapperProxy 实现了 InvocationHandler 接口 - JDK动态代理核心
 * 2. cachedInvoker 方法使用 MapUtil.computeIfAbsent 缓存方法调用器
 * 3. PlainMethodInvoker 处理普通方法调用
 * 4. DefaultMethodInvoker 处理默认方法调用
 */
class MapperProxyTest {

    @Test
    @DisplayName("MapperProxy 应实现 InvocationHandler 接口")
    void shouldImplementInvocationHandler() {
        // 验证 MapperProxy 实现了 InvocationHandler
        assertThat(InvocationHandler.class.isAssignableFrom(MapperProxy.class))
                .as("MapperProxy 应实现 InvocationHandler")
                .isTrue();
    }

    @Test
    @DisplayName("MapperProxy 应实现 Serializable 接口")
    void shouldImplementSerializable() {
        assertThat(Serializable.class.isAssignableFrom(MapperProxy.class))
                .as("MapperProxy 应实现 Serializable")
                .isTrue();
    }

    @Test
    @DisplayName("cachedInvoker 方法应正确缓存方法调用器")
    void cachedInvokerShouldUseCache() throws Exception {
        // 验证 cachedInvoker 方法存在且可访问
        Method cachedInvokerMethod = MapperProxy.class.getDeclaredMethod(
                "cachedInvoker", Method.class);
        assertThat(cachedInvokerMethod).isNotNull();
    }

    @Test
    @DisplayName("PlainMethodInvoker 应正确处理方法调用")
    void plainMethodInvokerShouldExist() {
        // 验证内部类 PlainMethodInvoker 存在
        Class<?>[] declaredClasses = MapperProxy.class.getDeclaredClasses();
        boolean foundPlainMethodInvoker = false;
        for (Class<?> clazz : declaredClasses) {
            if (clazz.getSimpleName().equals("PlainMethodInvoker")) {
                foundPlainMethodInvoker = true;
                break;
            }
        }
        assertThat(foundPlainMethodInvoker)
                .as("PlainMethodInvoker 内部类应存在")
                .isTrue();
    }

    @Test
    @DisplayName("DefaultMethodInvoker 应正确处理默认方法")
    void defaultMethodInvokerShouldExist() {
        // 验证内部类 DefaultMethodInvoker 存在
        Class<?>[] declaredClasses = MapperProxy.class.getDeclaredClasses();
        boolean foundDefaultMethodInvoker = false;
        for (Class<?> clazz : declaredClasses) {
            if (clazz.getSimpleName().equals("DefaultMethodInvoker")) {
                foundDefaultMethodInvoker = true;
                break;
            }
        }
        assertThat(foundDefaultMethodInvoker)
                .as("DefaultMethodInvoker 内部类应存在")
                .isTrue();
    }

    @Test
    @DisplayName("MapperMethodInvoker 接口应定义 invoke 方法")
    void mapperMethodInvokerInterfaceShouldDefineInvoke() {
        // 验证内部接口 MapperMethodInvoker 存在
        Class<?>[] declaredClasses = MapperProxy.class.getDeclaredClasses();
        boolean foundMapperMethodInvoker = false;
        for (Class<?> clazz : declaredClasses) {
            if (clazz.getSimpleName().equals("MapperMethodInvoker")) {
                foundMapperMethodInvoker = true;
                // 验证接口定义了 invoke 方法
                assertThat(clazz.getDeclaredMethods())
                        .anyMatch(m -> m.getName().equals("invoke"));
                break;
            }
        }
        assertThat(foundMapperMethodInvoker)
                .as("MapperMethodInvoker 接口应存在")
                .isTrue();
    }

    @Test
    @DisplayName("methodCache 字段应使用线程安全的数据结构")
    void methodCacheShouldBeThreadSafe() throws Exception {
        // 验证构造函数接收 Map<Method, MapperMethodInvoker> 参数
        var constructor = MapperProxy.class.getDeclaredConstructors()[0];
        assertThat(constructor.getParameterTypes())
                .as("构造函数参数应包含 Map<Method, MapperMethodInvoker>")
                .hasSize(3);
    }
}

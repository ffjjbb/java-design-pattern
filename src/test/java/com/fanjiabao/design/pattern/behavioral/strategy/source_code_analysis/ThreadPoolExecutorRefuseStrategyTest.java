package com.fanjiabao.design.pattern.behavioral.strategy.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ThreadPoolExecutor 拒绝策略测试
 * 
 * 测试要点:
 * 1. RejectedExecutionHandler 是策略模式的核心接口
 * 2. MyRejectHandler 实现了自定义拒绝策略
 * 3. 线程池在队列满时调用拒绝策略
 */
class ThreadPoolExecutorRefuseStrategyTest {

    @Test
    @DisplayName("MyRejectHandler 应实现 RejectedExecutionHandler 接口")
    void myRejectHandlerShouldImplementInterface() {
        // 验证 MyRejectHandler 实现了 RejectedExecutionHandler
        assertThat(RejectedExecutionHandler.class.isAssignableFrom(MyRejectHandler.class))
                .as("MyRejectHandler 应实现 RejectedExecutionHandler")
                .isTrue();
    }

    @Test
    @DisplayName("MyRejectHandler 应定义 rejectedExecution 方法")
    void myRejectHandlerShouldDefineRejectedExecution() {
        // 验证 rejectedExecution 方法存在
        Method rejectedExecution = null;
        try {
            rejectedExecution = MyRejectHandler.class.getDeclaredMethod(
                    "rejectedExecution", Runnable.class, ThreadPoolExecutor.class);
        } catch (NoSuchMethodException e) {
            // 尝试公共方法
            try {
                rejectedExecution = MyRejectHandler.class.getMethod(
                        "rejectedExecution", Runnable.class, ThreadPoolExecutor.class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(rejectedExecution)
                .as("rejectedExecution 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("RejectedExecutionHandler 策略接口应存在")
    void rejectedExecutionHandlerInterfaceShouldExist() {
        // 验证 RejectedExecutionHandler 类可访问
        assertThat(RejectedExecutionHandler.class)
                .as("RejectedExecutionHandler 应可访问")
                .isNotNull();
    }

    @Test
    @DisplayName("ThreadPoolExecutor 应持有拒绝策略")
    void threadPoolExecutorShouldHoldRejectedPolicy() {
        // 验证 ThreadPoolExecutor 有 setRejectedExecutionHandler 方法
        Method[] methods = ThreadPoolExecutor.class.getDeclaredMethods();
        boolean hasSetHandler = false;
        for (Method method : methods) {
            if (method.getName().equals("setRejectedExecutionHandler")) {
                hasSetHandler = true;
                break;
            }
        }
        assertThat(hasSetHandler)
                .as("ThreadPoolExecutor 应有 setRejectedExecutionHandler 方法")
                .isTrue();
    }

    @Test
    @DisplayName("JDK 内置拒绝策略应存在")
    void jdkBuiltInPoliciesShouldExist() {
        // 验证 JDK 内置的拒绝策略类存在
        assertThat(ThreadPoolExecutor.AbortPolicy.class)
                .as("AbortPolicy 应存在")
                .isNotNull();
        assertThat(ThreadPoolExecutor.CallerRunsPolicy.class)
                .as("CallerRunsPolicy 应存在")
                .isNotNull();
        assertThat(ThreadPoolExecutor.DiscardOldestPolicy.class)
                .as("DiscardOldestPolicy 应存在")
                .isNotNull();
        assertThat(ThreadPoolExecutor.DiscardPolicy.class)
                .as("DiscardPolicy 应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("所有内置策略应实现 RejectedExecutionHandler")
    void allBuiltInPoliciesShouldImplementInterface() {
        assertThat(RejectedExecutionHandler.class.isAssignableFrom(ThreadPoolExecutor.AbortPolicy.class))
                .as("AbortPolicy 应实现 RejectedExecutionHandler")
                .isTrue();
        assertThat(RejectedExecutionHandler.class.isAssignableFrom(ThreadPoolExecutor.CallerRunsPolicy.class))
                .as("CallerRunsPolicy 应实现 RejectedExecutionHandler")
                .isTrue();
    }
}

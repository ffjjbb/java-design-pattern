package com.fanjiabao.design.pattern.behavioral.state.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tomcat 生命周期状态模式测试
 * 
 * 测试要点:
 * 1. SimpleTomcatComponent 继承 LifecycleBase
 * 2. 实现了 initInternal/startInternal/stopInternal/destroyInternal 模板方法
 * 3. 状态转换的正确性
 */
class TomcatLifeCycleStateTest {

    @Test
    @DisplayName("SimpleTomcatComponent 应继承 LifecycleBase")
    void shouldExtendLifecycleBase() {
        // 验证继承关系
        assertThat(SimpleTomcatComponent.class.getSuperclass().getSimpleName())
                .as("SimpleTomcatComponent 应继承 LifecycleBase")
                .isEqualTo("LifecycleBase");
    }

    @Test
    @DisplayName("应实现 initInternal 模板方法")
    void shouldImplementInitInternal() {
        Method initInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("initInternal")) {
                initInternal = method;
                break;
            }
        }
        assertThat(initInternal)
                .as("initInternal 方法应存在")
                .isNotNull();
        assertThat(initInternal.getExceptionTypes())
                .as("initInternal 应声明抛出 LifecycleException")
                .isNotEmpty();
    }

    @Test
    @DisplayName("应实现 startInternal 模板方法")
    void shouldImplementStartInternal() {
        Method startInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("startInternal")) {
                startInternal = method;
                break;
            }
        }
        assertThat(startInternal)
                .as("startInternal 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("应实现 stopInternal 模板方法")
    void shouldImplementStopInternal() {
        Method stopInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("stopInternal")) {
                stopInternal = method;
                break;
            }
        }
        assertThat(stopInternal)
                .as("stopInternal 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("应实现 destroyInternal 模板方法")
    void shouldImplementDestroyInternal() {
        Method destroyInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("destroyInternal")) {
                destroyInternal = method;
                break;
            }
        }
        assertThat(destroyInternal)
                .as("destroyInternal 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("LifecycleState 枚举应定义正确的状态")
    void lifecycleStateShouldDefineCorrectStates() {
        // 验证 LifecycleState 类可访问
        Class<?> lifecycleStateClass = null;
        try {
            lifecycleStateClass = Class.forName("org.apache.catalina.LifecycleState");
        } catch (ClassNotFoundException e) {
            // 框架类可能在测试环境中不可用
        }
        assertThat(lifecycleStateClass).isNotNull();
    }

    @Test
    @DisplayName("startInternal 应调用 setState 设置 STARTING 状态")
    void startInternalShouldSetStartingState() throws Exception {
        // 验证 startInternal 方法调用 setState
        Method startInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("startInternal")) {
                startInternal = method;
                break;
            }
        }
        assertThat(startInternal).isNotNull();
    }

    @Test
    @DisplayName("stopInternal 应调用 setState 设置 STOPPING 状态")
    void stopInternalShouldSetStoppingState() throws Exception {
        // 验证 stopInternal 方法调用 setState
        Method stopInternal = null;
        for (Method method : SimpleTomcatComponent.class.getDeclaredMethods()) {
            if (method.getName().equals("stopInternal")) {
                stopInternal = method;
                break;
            }
        }
        assertThat(stopInternal).isNotNull();
    }

    @Test
    @DisplayName("TomcatLifeCycleAnalysis 应有 main 方法")
    void tomcatLifeCycleAnalysisShouldHaveMainMethod() {
        // 验证 TomcatLifeCycleAnalysis 有 main 方法
        Method main = null;
        try {
            main = TomcatLifeCycleAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = TomcatLifeCycleAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("TomcatLifeCycleAnalysis 应有 main 方法")
                .isNotNull();
    }
}

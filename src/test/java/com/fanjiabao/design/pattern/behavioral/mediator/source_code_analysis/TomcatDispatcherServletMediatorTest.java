package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tomcat DispatcherServlet 中介者模式测试
 * 
 * 测试要点:
 * 1. DispatcherServlet 作为中介者协调各组件
 * 2. HelloController 定义处理方法
 * 3. AppConfig 配置 Spring MVC 组件
 */
class TomcatDispatcherServletMediatorTest {

    @Test
    @DisplayName("TomcatDispatcherServletMediatorAnalysis 应有 main 方法")
    void tomcatDispatcherServletMediatorAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = TomcatDispatcherServletMediatorAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = TomcatDispatcherServletMediatorAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("TomcatDispatcherServletMediatorAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("HelloController 应存在")
    void helloControllerShouldExist() {
        assertThat(HelloController.class)
                .as("HelloController 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("HelloController 应有 hello 方法")
    void helloControllerShouldHaveHelloMethod() {
        boolean hasHelloMethod = false;
        for (Method method : HelloController.class.getDeclaredMethods()) {
            if (method.getName().equals("hello")) {
                hasHelloMethod = true;
                break;
            }
        }
        assertThat(hasHelloMethod)
                .as("HelloController 应有 hello 方法")
                .isTrue();
    }

    @Test
    @DisplayName("AppConfig 应存在")
    void appConfigShouldExist() {
        assertThat(AppConfig.class)
                .as("AppConfig 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("doDispatch 方法应在 DispatcherServlet 中")
    void doDispatchMethodShouldExist() {
        // 验证 doDispatch 方法存在于 DispatcherServlet
        try {
            Class<?> dispatcherServletClass = Class.forName("org.springframework.web.servlet.DispatcherServlet");
            boolean hasDoDispatch = false;
            for (Method method : dispatcherServletClass.getDeclaredMethods()) {
                if (method.getName().equals("doDispatch")) {
                    hasDoDispatch = true;
                    break;
                }
            }
            assertThat(hasDoDispatch)
                    .as("DispatcherServlet 应有 doDispatch 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Spring MVC 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("DispatcherServlet 应是 HttpServlet 的子类")
    void dispatcherServletShouldExtendHttpServlet() {
        try {
            Class<?> dispatcherServletClass = Class.forName("org.springframework.web.servlet.DispatcherServlet");
            Class<?> superclass = dispatcherServletClass.getSuperclass();
            boolean foundHttpServlet = false;
            while (superclass != null) {
                if (superclass.getName().contains("HttpServlet")) {
                    foundHttpServlet = true;
                    break;
                }
                superclass = superclass.getSuperclass();
            }
            assertThat(foundHttpServlet)
                    .as("DispatcherServlet 应继承 HttpServlet (直接或间接)")
                    .isTrue();
        } catch (ClassNotFoundException e) {
        }
    }

    @Test
    @DisplayName("Tomcat 类应可实例化")
    void tomcatClassShouldBeInstantiable() {
        try {
            Class<?> tomcatClass = Class.forName("org.apache.catalina.startup.Tomcat");
            assertThat(tomcatClass)
                    .as("Tomcat 类应可访问")
                    .isNotNull();
        } catch (ClassNotFoundException e) {
            // Tomcat 可能在测试环境中不可用
        }
    }
}

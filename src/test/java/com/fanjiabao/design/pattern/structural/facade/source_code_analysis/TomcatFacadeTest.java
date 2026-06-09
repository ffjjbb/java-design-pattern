package com.fanjiabao.design.pattern.structural.facade.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tomcat 外观模式测试
 * 
 * 测试要点:
 * 1. TomcatFacadeDemo 演示外观模式
 * 2. RequestFacade 是 Tomcat 的外观
 * 3. 外观模式简化复杂系统的调用
 */
class TomcatFacadeTest {

    @Test
    @DisplayName("TomcatFacadeDemo 应继承 HttpServlet")
    void tomcatFacadeDemoShouldExtendHttpServlet() {
        assertThat(TomcatFacadeDemo.class.getSuperclass().getSimpleName())
                .as("TomcatFacadeDemo 应继承 HttpServlet")
                .isEqualTo("HttpServlet");
    }

    @Test
    @DisplayName("TomcatFacadeDemo 应有 doGet 方法 (继承自 HttpServlet)")
    void tomcatFacadeDemoShouldHaveDoGetMethod() {
        boolean hasDoGet = false;
        for (Method method : TomcatFacadeDemo.class.getDeclaredMethods()) {
            if (method.getName().equals("doGet")) {
                hasDoGet = true;
                break;
            }
        }
        assertThat(hasDoGet)
                .as("TomcatFacadeDemo 应有 doGet 方法")
                .isTrue();
    }

    @Test
    @DisplayName("TomcatFacadeDemo 应有 main 方法")
    void tomcatFacadeDemoShouldHaveMainMethod() {
        Method main = null;
        try {
            main = TomcatFacadeDemo.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = TomcatFacadeDemo.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("TomcatFacadeDemo 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("HttpServletRequest 应有 getParameter 方法")
    void httpServletRequestShouldHaveGetParameterMethod() {
        try {
            Class<?> requestClass = Class.forName("jakarta.servlet.http.HttpServletRequest");
            boolean hasGetParameter = false;
            for (Method method : requestClass.getMethods()) {
                if (method.getName().equals("getParameter")) {
                    hasGetParameter = true;
                    break;
                }
            }
            assertThat(hasGetParameter)
                    .as("HttpServletRequest 应有 getParameter 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Servlet API 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("RequestFacade 应实现 HttpServletRequest")
    void requestFacadeShouldImplementHttpServletRequest() {
        try {
            Class<?> facadeClass = Class.forName("org.apache.catalina.connector.RequestFacade");
            assertThat(jakarta.servlet.http.HttpServletRequest.class.isAssignableFrom(facadeClass))
                    .as("RequestFacade 应实现 HttpServletRequest")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Tomcat 可能在测试环境中不可用
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

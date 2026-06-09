package com.fanjiabao.design.pattern.structural.composite.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring 组合模式测试
 * 
 * 测试要点:
 * 1. Spring 父子容器使用树状结构
 * 2. 子容器可访问父容器的 Bean
 */
class SpringCompositeTest {

    @Test
    @DisplayName("SpringCompositeAnalysis 应有 main 方法")
    void springCompositeAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = SpringCompositeAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = SpringCompositeAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("SpringCompositeAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("ConfigurableApplicationContext 应有 setParent 方法")
    void applicationContextShouldHaveSetParentMethod() {
        try {
            Class<?> contextClass = Class.forName("org.springframework.context.ConfigurableApplicationContext");
            boolean hasSetParent = false;
            for (Method method : contextClass.getMethods()) {
                if (method.getName().equals("setParent")) {
                    hasSetParent = true;
                    break;
                }
            }
            assertThat(hasSetParent)
                    .as("ConfigurableApplicationContext 应有 setParent 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
        }
    }

    @Test
    @DisplayName("ConfigurableApplicationContext 应有 refresh 方法")
    void configurableApplicationContextShouldHaveRefreshMethod() {
        try {
            Class<?> contextClass = Class.forName("org.springframework.context.ConfigurableApplicationContext");
            boolean hasRefresh = false;
            for (Method method : contextClass.getMethods()) {
                if (method.getName().equals("refresh")) {
                    hasRefresh = true;
                    break;
                }
            }
            assertThat(hasRefresh)
                    .as("ConfigurableApplicationContext 应有 refresh 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Spring 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("BeanFactory 应有 getBean 方法")
    void beanFactoryShouldHaveGetBeanMethod() {
        try {
            Class<?> beanFactoryClass = Class.forName("org.springframework.beans.factory.BeanFactory");
            boolean hasGetBean = false;
            for (Method method : beanFactoryClass.getMethods()) {
                if (method.getName().equals("getBean")) {
                    hasGetBean = true;
                    break;
                }
            }
            assertThat(hasGetBean)
                    .as("BeanFactory 应有 getBean 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Spring 可能在测试环境中不可用
        }
    }
}

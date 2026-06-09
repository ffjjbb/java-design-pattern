package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring BeanDefinitionVisitor 访问者模式测试
 * 
 * 测试要点:
 * 1. VisitorBeanDefinitionRegistryPostProcessor 实现访问者
 * 2. DataSourceConfigBean 是被访问的元素
 * 3. BeanDefinitionVisitor 是访问者角色
 */
class SpringBeanDefinitionVisitorTest {

    @Test
    @DisplayName("DataSourceConfigBean 应存在")
    void dataSourceConfigBeanShouldExist() {
        assertThat(DataSourceConfigBean.class)
                .as("DataSourceConfigBean 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("DataSourceConfigBean 应有 name 和 url 属性")
    void dataSourceConfigBeanShouldHaveNameAndUrl() {
        boolean hasName = false, hasUrl = false;
        for (var field : DataSourceConfigBean.class.getDeclaredFields()) {
            switch (field.getName()) {
                case "name" -> hasName = true;
                case "url" -> hasUrl = true;
            }
        }
        assertThat(hasName).as("DataSourceConfigBean 应有 name 属性").isTrue();
        assertThat(hasUrl).as("DataSourceConfigBean 应有 url 属性").isTrue();
    }

    @Test
    @DisplayName("DataSourceConfigBean 应有 setter 方法")
    void dataSourceConfigBeanShouldHaveGettersAndSetters() {
        try {
            assertThat(DataSourceConfigBean.class.getMethod("setName", String.class))
                    .as("DataSourceConfigBean 应有 setName 方法")
                    .isNotNull();
            assertThat(DataSourceConfigBean.class.getMethod("setUrl", String.class))
                    .as("DataSourceConfigBean 应有 setUrl 方法")
                    .isNotNull();
        } catch (NoSuchMethodException e) {
            throw new AssertionError("方法不存在: " + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("VisitorBeanDefinitionRegistryPostProcessor 应存在")
    void visitorBeanDefinitionRegistryPostProcessorShouldExist() {
        assertThat(VisitorBeanDefinitionRegistryPostProcessor.class)
                .as("VisitorBeanDefinitionRegistryPostProcessor 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("VisitorBeanDefinitionRegistryPostProcessor 应实现 BeanDefinitionRegistryPostProcessor")
    void visitorBeanDefinitionRegistryPostProcessorShouldImplementInterface() {
        boolean implementsInterface = false;
        for (var iface : VisitorBeanDefinitionRegistryPostProcessor.class.getInterfaces()) {
            if (iface.getSimpleName().contains("BeanDefinitionRegistryPostProcessor")) {
                implementsInterface = true;
                break;
            }
        }
        assertThat(implementsInterface)
                .as("VisitorBeanDefinitionRegistryPostProcessor 应实现 BeanDefinitionRegistryPostProcessor")
                .isTrue();
    }

    @Test
    @DisplayName("VisitorBeanDefinitionRegistryPostProcessor 应有 postProcessBeanDefinitionRegistry 方法")
    void visitorBeanDefinitionRegistryPostProcessorShouldHavePostProcessBeanDefinitionRegistry() {
        boolean hasMethod = false;
        for (Method method : VisitorBeanDefinitionRegistryPostProcessor.class.getDeclaredMethods()) {
            if (method.getName().equals("postProcessBeanDefinitionRegistry")) {
                hasMethod = true;
                break;
            }
        }
        assertThat(hasMethod)
                .as("VisitorBeanDefinitionRegistryPostProcessor 应有 postProcessBeanDefinitionRegistry 方法")
                .isTrue();
    }

    @Test
    @DisplayName("VisitorBeanDefinitionRegistryPostProcessor 应有 postProcessBeanFactory 方法")
    void visitorBeanDefinitionRegistryPostProcessorShouldHavePostProcessBeanFactory() {
        boolean hasMethod = false;
        for (Method method : VisitorBeanDefinitionRegistryPostProcessor.class.getDeclaredMethods()) {
            if (method.getName().equals("postProcessBeanFactory")) {
                hasMethod = true;
                break;
            }
        }
        assertThat(hasMethod)
                .as("VisitorBeanDefinitionRegistryPostProcessor 应有 postProcessBeanFactory 方法")
                .isTrue();
    }

    @Test
    @DisplayName("SpringBeanDefinitionVisitorAnalysis 应有 main 方法")
    void springBeanDefinitionVisitorAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = SpringBeanDefinitionVisitorAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = SpringBeanDefinitionVisitorAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("SpringBeanDefinitionVisitorAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("BeanDefinitionVisitor 类应可访问")
    void beanDefinitionVisitorClassShouldBeAccessible() {
        try {
            Class<?> beanDefinitionVisitorClass = Class.forName("org.springframework.beans.factory.config.BeanDefinitionVisitor");
            assertThat(beanDefinitionVisitorClass)
                    .as("BeanDefinitionVisitor 类应可访问")
                    .isNotNull();
        } catch (ClassNotFoundException e) {
            // Spring 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("BeanDefinitionVisitor 应有 visitBeanDefinition 方法")
    void beanDefinitionVisitorShouldHaveVisitBeanDefinitionMethod() {
        try {
            Class<?> beanDefinitionVisitorClass = Class.forName("org.springframework.beans.factory.config.BeanDefinitionVisitor");
            boolean hasVisitBeanDefinition = false;
            for (Method method : beanDefinitionVisitorClass.getDeclaredMethods()) {
                if (method.getName().equals("visitBeanDefinition")) {
                    hasVisitBeanDefinition = true;
                    break;
                }
            }
            assertThat(hasVisitBeanDefinition)
                    .as("BeanDefinitionVisitor 应有 visitBeanDefinition 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Spring 可能在测试环境中不可用
        }
    }
}

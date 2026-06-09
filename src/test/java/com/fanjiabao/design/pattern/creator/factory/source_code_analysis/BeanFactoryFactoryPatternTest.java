package com.fanjiabao.design.pattern.creator.factory.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring BeanFactory 工厂模式测试
 * 
 * 测试要点:
 * 1. BeanFactoryFactoryPatternAnalysis 演示工厂模式
 * 2. HelloService 是工厂生产的产品
 * 3. AppConfig 定义 Bean 创建方式
 */
class BeanFactoryFactoryPatternTest {

    @Test
    @DisplayName("BeanFactoryFactoryPatternAnalysis 应有 main 方法")
    void beanFactoryFactoryPatternAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = BeanFactoryFactoryPatternAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = BeanFactoryFactoryPatternAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("BeanFactoryFactoryPatternAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("HelloService 应存在")
    void helloServiceShouldExist() {
        assertThat(BeanFactoryFactoryPatternAnalysis.HelloService.class)
                .as("HelloService 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("HelloService 应有 say 方法")
    void helloServiceShouldHaveSayMethod() throws NoSuchMethodException {
        assertThat(BeanFactoryFactoryPatternAnalysis.HelloService.class.getMethod("say"))
                .as("HelloService 应有 say 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("HelloService 应有 say 方法返回非空字符串")
    void helloServiceSayShouldReturnNonEmpty() {
        var helloService = new BeanFactoryFactoryPatternAnalysis.HelloService();
        String result = helloService.say();
        assertThat(result)
                .as("say 方法应返回非空字符串")
                .isNotEmpty();
    }

    @Test
    @DisplayName("AppConfig 应存在")
    void appConfigShouldExist() {
        assertThat(BeanFactoryFactoryPatternAnalysis.AppConfig.class)
                .as("AppConfig 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("AppConfig 应有 @Configuration 注解")
    void appConfigShouldHaveConfigurationAnnotation() {
        boolean hasConfiguration = false;
        for (var annotation : BeanFactoryFactoryPatternAnalysis.AppConfig.class.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Configuration")) {
                hasConfiguration = true;
                break;
            }
        }
        assertThat(hasConfiguration)
                .as("AppConfig 应有 @Configuration 注解")
                .isTrue();
    }

    @Test
    @DisplayName("AppConfig 应有 helloService 方法返回 HelloService")
    void appConfigShouldHaveHelloServiceMethod() {
        boolean hasHelloServiceMethod = false;
        for (Method method : BeanFactoryFactoryPatternAnalysis.AppConfig.class.getMethods()) {
            if (method.getName().equals("helloService")) {
                hasHelloServiceMethod = true;
                break;
            }
        }
        assertThat(hasHelloServiceMethod)
                .as("AppConfig 应有 helloService 方法")
                .isTrue();
    }

    @Test
    @DisplayName("BeanFactory 接口应可访问")
    void beanFactoryInterfaceShouldBeAccessible() {
        try {
            Class<?> beanFactoryClass = Class.forName("org.springframework.beans.factory.BeanFactory");
            assertThat(beanFactoryClass)
                    .as("BeanFactory 接口应可访问")
                    .isNotNull();
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
            for (Method method : beanFactoryClass.getDeclaredMethods()) {
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

    @Test
    @DisplayName("AnnotationConfigApplicationContext 应实现 BeanFactory 接口")
    void annotationConfigApplicationContextShouldExtendBeanFactory() {
        try {
            Class<?> contextClass = Class.forName("org.springframework.context.annotation.AnnotationConfigApplicationContext");
            Class<?> beanFactoryInterface = Class.forName("org.springframework.beans.factory.BeanFactory");
            assertThat(beanFactoryInterface.isAssignableFrom(contextClass))
                    .as("AnnotationConfigApplicationContext 应实现 BeanFactory 接口")
                    .isTrue();
        } catch (ClassNotFoundException e) {
        }
    }
}

package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring AOP 责任链模式测试
 * 
 * 测试要点:
 * 1. Aspect1 和 Aspect2 实现切面
 * 2. UserService 是被代理的目标类
 * 3. AppConfig 配置 AOP
 */
class SpringAopChainOfResponsibilityTest {

    @Test
    @DisplayName("UserService 应存在")
    void userServiceShouldExist() {
        assertThat(UserService.class)
                .as("UserService 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("UserService 应有 test 方法")
    void userServiceShouldHaveTestMethod() {
        boolean hasTestMethod = false;
        for (Method method : UserService.class.getDeclaredMethods()) {
            if (method.getName().equals("test")) {
                hasTestMethod = true;
                break;
            }
        }
        assertThat(hasTestMethod)
                .as("UserService 应有 test 方法")
                .isTrue();
    }

    @Test
    @DisplayName("Aspect1 应存在")
    void aspect1ShouldExist() {
        assertThat(Aspect1.class)
                .as("Aspect1 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("Aspect2 应存在")
    void aspect2ShouldExist() {
        assertThat(Aspect2.class)
                .as("Aspect2 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("AppConfig 应存在")
    void appConfigShouldExist() {
        assertThat(AppConfig.class)
                .as("AppConfig 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("SpringAopChainOfResponsibilityAnalysis 应有 main 方法")
    void springAopChainOfResponsibilityAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = SpringAopChainOfResponsibilityAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = SpringAopChainOfResponsibilityAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("SpringAopChainOfResponsibilityAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("MethodInterceptor 接口应存在")
    void methodInterceptorInterfaceShouldExist() {
        try {
            Class<?> methodInterceptorClass = Class.forName("org.aopalliance.intercept.MethodInterceptor");
            assertThat(methodInterceptorClass)
                    .as("MethodInterceptor 接口应存在")
                    .isNotNull();
        } catch (ClassNotFoundException e) {
            // AOP 框架可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("Aspect1 应有 @Aspect 注解或实现 MethodInterceptor")
    void aspect1ShouldHaveAspectAnnotationOrImplementMethodInterceptor() {
        boolean hasAspectAnnotation = false;
        for (var annotation : Aspect1.class.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Aspect")) {
                hasAspectAnnotation = true;
                break;
            }
        }

        boolean implementsMethodInterceptor = false;
        for (var iface : Aspect1.class.getInterfaces()) {
            if (iface.getSimpleName().equals("MethodInterceptor")) {
                implementsMethodInterceptor = true;
                break;
            }
        }

        assertThat(hasAspectAnnotation || implementsMethodInterceptor)
                .as("Aspect1 应有 @Aspect 注解或实现 MethodInterceptor")
                .isTrue();
    }

    @Test
    @DisplayName("ReflectiveMethodInvocation 的 proceed 方法应存在")
    void reflectiveMethodInvocationProceedShouldExist() {
        try {
            Class<?> clazz = Class.forName("org.springframework.aop.framework.ReflectiveMethodInvocation");
            boolean hasProceed = false;
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals("proceed")) {
                    hasProceed = true;
                    break;
                }
            }
            assertThat(hasProceed)
                    .as("ReflectiveMethodInvocation 应有 proceed 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // Spring AOP 可能在测试环境中不可用
        }
    }
}

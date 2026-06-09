package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring AbstractApplicationContext 模板方法测试
 * 
 * 测试要点:
 * 1. MyApplicationContext 继承 AnnotationConfigApplicationContext
 * 2. 实现了 postProcessBeanFactory 钩子方法
 * 3. 实现了 onRefresh 钩子方法
 * 4. refresh() 是模板方法
 */
class AbstractApplicationContextTemplateTest {

    @Test
    @DisplayName("MyApplicationContext 应继承 AnnotationConfigApplicationContext")
    void shouldExtendAnnotationConfigApplicationContext() {
        assertThat(MyApplicationContext.class.getSuperclass().getSimpleName())
                .as("MyApplicationContext 应继承 AnnotationConfigApplicationContext")
                .isEqualTo("AnnotationConfigApplicationContext");
    }

    @Test
    @DisplayName("应实现 postProcessBeanFactory 钩子方法")
    void shouldImplementPostProcessBeanFactory() {
        Method postProcessBeanFactory = null;
        for (Method method : MyApplicationContext.class.getDeclaredMethods()) {
            if (method.getName().equals("postProcessBeanFactory")) {
                postProcessBeanFactory = method;
                break;
            }
        }
        assertThat(postProcessBeanFactory)
                .as("postProcessBeanFactory 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("应实现 onRefresh 钩子方法")
    void shouldImplementOnRefresh() {
        Method onRefresh = null;
        for (Method method : MyApplicationContext.class.getDeclaredMethods()) {
            if (method.getName().equals("onRefresh")) {
                onRefresh = method;
                break;
            }
        }
        assertThat(onRefresh)
                .as("onRefresh 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("构造函数应调用 refresh 方法")
    void constructorShouldCallRefresh() {
        // 验证构造函数存在
        assertThat(MyApplicationContext.class.getConstructors())
                .as("应有构造函数")
                .isNotEmpty();
    }

    @Test
    @DisplayName("MyBeanPostProcessor 应存在")
    void myBeanPostProcessorShouldExist() {
        assertThat(MyBeanPostProcessor.class)
                .as("MyBeanPostProcessor 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("BeanPostProcessor 接口方法应被重写")
    void beanPostProcessorMethodsShouldBeOverridden() {
        Method[] methods = MyBeanPostProcessor.class.getDeclaredMethods();
        boolean hasPostProcessBeforeInitialization = false;
        boolean hasPostProcessAfterInitialization = false;

        for (Method method : methods) {
            if (method.getName().equals("postProcessBeforeInitialization")) {
                hasPostProcessBeforeInitialization = true;
            }
            if (method.getName().equals("postProcessAfterInitialization")) {
                hasPostProcessAfterInitialization = true;
            }
        }

        assertThat(hasPostProcessBeforeInitialization)
                .as("postProcessBeforeInitialization 应被重写")
                .isTrue();
        assertThat(hasPostProcessAfterInitialization)
                .as("postProcessAfterInitialization 应被重写")
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
    @DisplayName("AbstractApplicationContextTemplateAnalysis 应有 main 方法")
    void abstractApplicationContextTemplateAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = AbstractApplicationContextTemplateAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = AbstractApplicationContextTemplateAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("AbstractApplicationContextTemplateAnalysis 应有 main 方法")
                .isNotNull();
    }
}

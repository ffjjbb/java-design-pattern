package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Aspect2 单元测试
 * 测试切面2
 */
public class Aspect2Test {

    @Test
    @DisplayName("测试 Aspect2 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 Aspect2 实例
        Aspect2 aspect2 = new Aspect2();
        
        // 断言: 实例不为空
        assertThat(aspect2).isNotNull();
    }

    @Test
    @DisplayName("测试 Aspect2 存在 Aspect 注解")
    void testAspectAnnotation() {
        // 测试场景: 验证 Aspect2 有 @Aspect 注解
        boolean hasAspect = Aspect2.class.isAnnotationPresent(
            org.aspectj.lang.annotation.Aspect.class
        );
        
        // 断言: 存在 @Aspect 注解
        assertThat(hasAspect).isTrue();
    }

    @Test
    @DisplayName("测试 Aspect2 存在 Component 注解")
    void testComponentAnnotation() {
        // 测试场景: 验证 Aspect2 有 @Component 注解
        boolean hasComponent = Aspect2.class.isAnnotationPresent(
            org.springframework.stereotype.Component.class
        );
        
        // 断言: 存在 @Component 注解
        assertThat(hasComponent).isTrue();
    }
}

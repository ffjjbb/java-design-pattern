package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Aspect1 单元测试
 * 测试切面1
 */
public class Aspect1Test {

    @Test
    @DisplayName("测试 Aspect1 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 Aspect1 实例
        Aspect1 aspect1 = new Aspect1();
        
        // 断言: 实例不为空
        assertThat(aspect1).isNotNull();
    }

    @Test
    @DisplayName("测试 Aspect1 存在 Aspect 注解")
    void testAspectAnnotation() {
        // 测试场景: 验证 Aspect1 有 @Aspect 注解
        boolean hasAspect = Aspect1.class.isAnnotationPresent(
            org.aspectj.lang.annotation.Aspect.class
        );
        
        // 断言: 存在 @Aspect 注解
        assertThat(hasAspect).isTrue();
    }

    @Test
    @DisplayName("测试 Aspect1 存在 Component 注解")
    void testComponentAnnotation() {
        // 测试场景: 验证 Aspect1 有 @Component 注解
        boolean hasComponent = Aspect1.class.isAnnotationPresent(
            org.springframework.stereotype.Component.class
        );
        
        // 断言: 存在 @Component 注解
        assertThat(hasComponent).isTrue();
    }
}

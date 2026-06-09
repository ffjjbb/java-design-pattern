package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AppConfig 单元测试
 * 测试中介者模式示例配置类
 */
public class AppConfigTest {

    @Test
    @DisplayName("测试 AppConfig 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 AppConfig 实例
        AppConfig config = new AppConfig();
        
        // 断言: 实例不为空
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("测试 AppConfig 类存在 Configuration 注解")
    void testConfigurationAnnotation() {
        // 测试场景: 验证 AppConfig 有 @Configuration 注解
        boolean hasConfiguration = AppConfig.class.isAnnotationPresent(
            org.springframework.context.annotation.Configuration.class
        );
        
        // 断言: 存在 @Configuration 注解
        assertThat(hasConfiguration).isTrue();
    }

    @Test
    @DisplayName("测试 AppConfig 类存在 EnableWebMvc 注解")
    void testEnableWebMvcAnnotation() {
        // 测试场景: 验证 AppConfig 有 @EnableWebMvc 注解
        boolean hasEnableWebMvc = AppConfig.class.isAnnotationPresent(
            org.springframework.web.servlet.config.annotation.EnableWebMvc.class
        );
        
        // 断言: 存在 @EnableWebMvc 注解
        assertThat(hasEnableWebMvc).isTrue();
    }

    @Test
    @DisplayName("测试 AppConfig 类存在 ComponentScan 注解")
    void testComponentScanAnnotation() {
        // 测试场景: 验证 AppConfig 有 @ComponentScan 注解
        boolean hasComponentScan = AppConfig.class.isAnnotationPresent(
            org.springframework.context.annotation.ComponentScan.class
        );
        
        // 断言: 存在 @ComponentScan 注解
        assertThat(hasComponentScan).isTrue();
    }
}

package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * VisitorBeanDefinitionRegistryPostProcessor 单元测试
 * 测试访问者模式的 Bean 定义注册后置处理器
 */
public class VisitorBeanDefinitionRegistryPostProcessorTest {

    @Test
    @DisplayName("测试 VisitorBeanDefinitionRegistryPostProcessor 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 VisitorBeanDefinitionRegistryPostProcessor 实例
        VisitorBeanDefinitionRegistryPostProcessor processor = new VisitorBeanDefinitionRegistryPostProcessor();
        
        // 断言: 实例不为空
        assertThat(processor).isNotNull();
    }
}

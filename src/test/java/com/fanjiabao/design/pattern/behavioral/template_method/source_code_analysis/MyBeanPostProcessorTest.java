package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MyBeanPostProcessor 单元测试
 * 测试自定义 Bean 后置处理器
 */
public class MyBeanPostProcessorTest {

    @Test
    @DisplayName("测试 MyBeanPostProcessor 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 MyBeanPostProcessor 实例
        MyBeanPostProcessor processor = new MyBeanPostProcessor();
        
        // 断言: 实例不为空
        assertThat(processor).isNotNull();
    }
}

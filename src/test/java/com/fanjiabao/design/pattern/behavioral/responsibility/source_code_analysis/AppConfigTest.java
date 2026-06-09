package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AppConfig 单元测试
 * 测试责任链模式示例配置类
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
}

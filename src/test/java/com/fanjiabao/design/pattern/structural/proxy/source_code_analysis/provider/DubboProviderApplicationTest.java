package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DubboProviderApplication 单元测试
 * 测试 Dubbo 提供者应用
 */
public class DubboProviderApplicationTest {

    @Test
    @DisplayName("测试 DubboProviderApplication 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 DubboProviderApplication 实例
        DubboProviderApplication app = new DubboProviderApplication();
        
        // 断言: 实例不为空
        assertThat(app).isNotNull();
    }
}

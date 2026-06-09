package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DubboConsumerApplication 单元测试
 * 测试 Dubbo 消费者应用
 */
public class DubboConsumerApplicationTest {

    @Test
    @DisplayName("测试 DubboConsumerApplication 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 DubboConsumerApplication 实例
        DubboConsumerApplication app = new DubboConsumerApplication();
        
        // 断言: 实例不为空
        assertThat(app).isNotNull();
    }
}

package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GreetingService 单元测试
 * 测试 Dubbo 服务接口
 */
public class GreetingServiceTest {

    @Test
    @DisplayName("测试 GreetingService 是接口")
    void testIsInterface() {
        // 测试场景: 验证 GreetingService 是接口
        boolean isInterface = GreetingService.class.isInterface();
        
        // 断言: 是接口
        assertThat(isInterface).isTrue();
    }
}

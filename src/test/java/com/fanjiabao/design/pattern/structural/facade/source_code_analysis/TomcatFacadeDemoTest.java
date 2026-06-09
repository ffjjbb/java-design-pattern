package com.fanjiabao.design.pattern.structural.facade.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TomcatFacadeDemo 单元测试
 * 测试 Tomcat 外观模式示例
 */
public class TomcatFacadeDemoTest {

    @Test
    @DisplayName("测试 TomcatFacadeDemo 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 TomcatFacadeDemo 实例
        TomcatFacadeDemo demo = new TomcatFacadeDemo();
        
        // 断言: 实例不为空
        assertThat(demo).isNotNull();
    }
}

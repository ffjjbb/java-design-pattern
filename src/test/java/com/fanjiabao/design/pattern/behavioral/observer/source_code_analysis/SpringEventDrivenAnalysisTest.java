package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SpringEventDrivenAnalysis 单元测试
 * 测试 Spring 事件驱动观察者模式分析
 */
public class SpringEventDrivenAnalysisTest {

    @Test
    @DisplayName("测试 main 方法执行成功")
    void testMainMethod() {
        // 测试场景: 验证 main 方法可以正常执行
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            SpringEventDrivenAnalysis.main(new String[]{});
            
            String output = outContent.toString();
            // 断言: 输出包含订单创建和短信发送的日志
            assertThat(output).contains("创建订单");
            assertThat(output).contains("发送短信");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 SpringEventDrivenAnalysis 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建分析类实例
        SpringEventDrivenAnalysis analysis = new SpringEventDrivenAnalysis();
        
        // 断言: 实例不为空
        assertThat(analysis).isNotNull();
    }
}

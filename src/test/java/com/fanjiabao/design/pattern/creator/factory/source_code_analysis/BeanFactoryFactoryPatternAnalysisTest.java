package com.fanjiabao.design.pattern.creator.factory.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BeanFactoryFactoryPatternAnalysis 单元测试
 * 测试 Spring BeanFactory 工厂模式分析
 */
public class BeanFactoryFactoryPatternAnalysisTest {

    @Test
    @DisplayName("测试 main 方法执行成功")
    void testMainMethod() {
        // 测试场景: 验证 main 方法可以正常执行
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            BeanFactoryFactoryPatternAnalysis.main(new String[]{});
            
            String output = outContent.toString();
            // 断言: 输出包含工厂模式的返回值
            assertThat(output).contains("工厂模式");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 BeanFactoryFactoryPatternAnalysis 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建分析类实例
        BeanFactoryFactoryPatternAnalysis analysis = new BeanFactoryFactoryPatternAnalysis();
        
        // 断言: 实例不为空
        assertThat(analysis).isNotNull();
    }
}

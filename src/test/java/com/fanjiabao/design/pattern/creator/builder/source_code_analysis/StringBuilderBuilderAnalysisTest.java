package com.fanjiabao.design.pattern.creator.builder.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StringBuilderBuilderAnalysis 单元测试
 * 测试 StringBuilder 建造者模式示例
 */
public class StringBuilderBuilderAnalysisTest {

    @Test
    @DisplayName("测试 main 方法执行并输出正确结果")
    void testMainMethod() {
        // 测试场景: 验证 main 方法正确执行并输出预期结果
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            StringBuilderBuilderAnalysis.main(new String[]{});
            
            String output = outContent.toString();
            // 断言: 输出应为 "Hello World"
            assertThat(output.trim()).isEqualTo("Hello World");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 StringBuilder 链式调用构建字符串")
    void testStringBuilderChainedCalls() {
        // 测试场景: 验证 StringBuilder 链式调用
        StringBuilder sb = new StringBuilder();
        sb.append("Hello").append(" ").append("World");
        String result = sb.toString();
        
        // 断言: 链式调用正确构建字符串
        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("测试 StringBuilder 空构建")
    void testEmptyStringBuilder() {
        // 测试场景: 验证空 StringBuilder
        StringBuilder sb = new StringBuilder();
        String result = sb.toString();
        
        // 断言: 空 StringBuilder 返回空字符串
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("测试 StringBuilder 追加不同类型")
    void testAppendDifferentTypes() {
        // 测试场景: 验证 StringBuilder 追加不同类型数据
        StringBuilder sb = new StringBuilder();
        sb.append("Value: ").append(100).append(", ").append(true);
        String result = sb.toString();
        
        // 断言: 正确追加不同类型
        assertThat(result).isEqualTo("Value: 100, true");
    }

    @Test
    @DisplayName("测试 StringBuilder 容量自动扩展")
    void testCapacityExpansion() {
        // 测试场景: 验证 StringBuilder 容量自动扩展
        StringBuilder sb = new StringBuilder(10);
        int initialCapacity = sb.capacity();
        
        sb.append("This is a very long string that exceeds initial capacity");
        
        // 断言: 容量自动扩展
        assertThat(sb.capacity()).isGreaterThanOrEqualTo(initialCapacity);
        assertThat(sb.length()).isGreaterThan(initialCapacity);
    }
}

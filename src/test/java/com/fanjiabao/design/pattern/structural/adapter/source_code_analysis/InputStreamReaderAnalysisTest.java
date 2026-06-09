package com.fanjiabao.design.pattern.structural.adapter.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * InputStreamReaderAnalysis 单元测试
 * 测试 InputStreamReader 适配器模式分析
 */
public class InputStreamReaderAnalysisTest {

    @Test
    @DisplayName("测试 main 方法执行成功")
    void testMainMethod() throws Exception {
        // 测试场景: 验证 main 方法可以正常执行
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            InputStreamReaderAnalysis.main(new String[]{});
            
            String output = outContent.toString();
            // 断言: 输出包含字节流和字符流的对比说明
            assertThat(output).contains("InputStream");
            assertThat(output).contains("InputStreamReader");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 InputStreamReaderAnalysis 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建分析类实例
        InputStreamReaderAnalysis analysis = new InputStreamReaderAnalysis();
        
        // 断言: 实例不为空
        assertThat(analysis).isNotNull();
    }
}

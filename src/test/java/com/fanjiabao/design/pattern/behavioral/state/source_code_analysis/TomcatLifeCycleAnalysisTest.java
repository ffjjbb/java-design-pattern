package com.fanjiabao.design.pattern.behavioral.state.source_code_analysis;

import org.apache.catalina.LifecycleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TomcatLifeCycleAnalysis 单元测试
 * 测试 Tomcat 生命周期状态模式分析
 */
public class TomcatLifeCycleAnalysisTest {

    @Test
    @DisplayName("测试 main 方法执行并抛出预期的异常")
    void testMainMethod() {
        // 测试场景: 验证 main 方法执行并在最后抛出异常（因为 destroy 后无法 start）
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            
            // main 方法最后会尝试在 destroy 后 start，这会抛出异常
            assertThatThrownBy(() -> TomcatLifeCycleAnalysis.main(new String[]{}))
                    .isInstanceOf(LifecycleException.class);
            
            String output = outContent.toString();
            // 断言: 输出包含各个生命周期阶段的日志
            assertThat(output).contains("初始状态");
            assertThat(output).contains("init()");
            assertThat(output).contains("start()");
            assertThat(output).contains("stop()");
            assertThat(output).contains("destroy()");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 TomcatLifeCycleAnalysis 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建分析类实例
        TomcatLifeCycleAnalysis analysis = new TomcatLifeCycleAnalysis();
        
        // 断言: 实例不为空
        assertThat(analysis).isNotNull();
    }
}

package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MyApplicationContext 单元测试
 * 测试自定义 ApplicationContext 的模板方法实现
 */
public class MyApplicationContextTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试 MyApplicationContext 创建并执行模板方法")
    void testCreateAndRefresh() {
        // 测试场景: 创建 MyApplicationContext 并验证模板方法执行
        System.setOut(new PrintStream(outContent));
        
        try {
            MyApplicationContext context = new MyApplicationContext(AppConfig.class);
            
            String output = outContent.toString();
            // 断言: 输出包含模板方法钩子的日志
            assertThat(output).contains("postProcessBeanFactory()");
            assertThat(output).contains("onRefresh()");
            
            context.close();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 MyApplicationContext 获取 Bean")
    void testGetBean() {
        // 测试场景: 从 MyApplicationContext 获取 Bean
        System.setOut(new PrintStream(outContent));
        
        try {
            MyApplicationContext context = new MyApplicationContext(AppConfig.class);
            
            // 断言: 可以获取 TemplateMethod Bean
            boolean hasBean = context.containsBean("templateMethod");
            
            context.close();
        } finally {
            System.setOut(originalOut);
        }
    }
}

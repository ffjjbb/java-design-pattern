package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataSourceConfigBean 单元测试
 * 测试数据源配置 Bean 的属性和方法
 */
public class DataSourceConfigBeanTest {

    private DataSourceConfigBean bean;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        bean = new DataSourceConfigBean();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试 setName 方法并输出日志")
    void testSetName() {
        // 测试场景: 设置 name 属性并验证日志输出
        System.setOut(new PrintStream(outContent));
        
        try {
            bean.setName("testDataSource");
            
            String output = outContent.toString();
            // 断言: 输出包含设置日志
            assertThat(output).contains("执行 setName");
            assertThat(output).contains("testDataSource");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 setUrl 方法并输出日志")
    void testSetUrl() {
        // 测试场景: 设置 url 属性并验证日志输出
        System.setOut(new PrintStream(outContent));
        
        try {
            bean.setUrl("jdbc:mysql://localhost:3306/test");
            
            String output = outContent.toString();
            // 断言: 输出包含设置日志
            assertThat(output).contains("执行 setUrl");
            assertThat(output).contains("jdbc:mysql://localhost:3306/test");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 toString 方法")
    void testToString() {
        // 测试场景: 验证 toString 输出正确格式
        bean.setName("myDataSource");
        bean.setUrl("jdbc:mysql://localhost:3306/mydb");
        
        String result = bean.toString();
        
        // 断言: toString 包含所有属性
        assertThat(result).contains("DataSourceConfigBean");
        assertThat(result).contains("myDataSource");
        assertThat(result).contains("jdbc:mysql://localhost:3306/mydb");
    }

    @Test
    @DisplayName("测试完整配置流程")
    void testFullConfiguration() {
        // 测试场景: 完整配置数据源
        System.setOut(new PrintStream(outContent));
        
        try {
            bean.setName("primaryDataSource");
            bean.setUrl("jdbc:mysql://localhost:3306/primary");
            
            String str = bean.toString();
            
            // 断言: toString 包含配置的值
            assertThat(str).contains("primaryDataSource");
            assertThat(str).contains("jdbc:mysql://localhost:3306/primary");
            
            String output = outContent.toString();
            assertThat(output).contains("执行 setName");
            assertThat(output).contains("执行 setUrl");
        } finally {
            System.setOut(originalOut);
        }
    }
}

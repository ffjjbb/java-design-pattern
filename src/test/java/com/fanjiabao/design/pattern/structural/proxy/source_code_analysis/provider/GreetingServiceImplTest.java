package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GreetingServiceImpl 单元测试
 * 测试 Dubbo 服务实现
 */
public class GreetingServiceImplTest {

    private GreetingServiceImpl greetingService;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        greetingService = new GreetingServiceImpl();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试 say 方法返回正确结果")
    void testSayMethod() {
        // 测试场景: 调用 say 方法并验证返回值
        System.setOut(new PrintStream(outContent));
        
        try {
            String result = greetingService.say("Hello");
            
            // 断言: 返回值正确
            assertThat(result).isEqualTo("它说 Hello");
            
            String output = outContent.toString();
            assertThat(output).contains("GreetingServiceImpl.say()");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 say 方法处理不同输入")
    void testSayWithDifferentInputs() {
        // 测试场景: 测试不同输入的 say 方法
        System.setOut(new PrintStream(outContent));
        
        try {
            String result1 = greetingService.say("World");
            String result2 = greetingService.say("Dubbo");
            
            // 断言: 返回值正确
            assertThat(result1).isEqualTo("它说 World");
            assertThat(result2).isEqualTo("它说 Dubbo");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 say 方法处理空字符串")
    void testSayWithEmptyString() {
        // 测试场景: 测试空字符串输入
        String result = greetingService.say("");
        
        // 断言: 返回值正确处理空字符串
        assertThat(result).isEqualTo("它说 ");
    }

    @Test
    @DisplayName("测试 say 方法处理中文")
    void testSayWithChinese() {
        // 测试场景: 测试中文输入
        String result = greetingService.say("你好");
        
        // 断言: 返回值正确处理中文
        assertThat(result).isEqualTo("它说 你好");
    }

    @Test
    @DisplayName("测试 GreetingServiceImpl 实现 GreetingService 接口")
    void testImplementsInterface() {
        // 测试场景: 验证类实现了正确的接口
        boolean implementsInterface = com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api.GreetingService.class
                .isAssignableFrom(GreetingServiceImpl.class);
        
        // 断言: 实现了接口
        assertThat(implementsInterface).isTrue();
    }
}

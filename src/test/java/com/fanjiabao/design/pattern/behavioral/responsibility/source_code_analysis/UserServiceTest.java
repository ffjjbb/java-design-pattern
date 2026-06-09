package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserService 单元测试
 * 测试目标类的方法执行
 */
public class UserServiceTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private UserService userService;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        userService = new UserService();
    }

    @Test
    @DisplayName("测试 test 方法执行并输出正确消息")
    void testMethodExecution() {
        // 测试场景: 验证 test 方法输出正确消息
        System.setOut(new PrintStream(outContent));
        
        try {
            userService.test();
            
            String output = outContent.toString();
            // 断言: 输出包含预期消息
            assertThat(output).contains("执行目标方法");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试多次调用 test 方法")
    void testMultipleCalls() {
        // 测试场景: 验证多次调用 test 方法
        System.setOut(new PrintStream(outContent));
        
        try {
            userService.test();
            userService.test();
            userService.test();
            
            String output = outContent.toString();
            // 断言: 消息出现3次
            assertThat(output).contains("执行目标方法");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 UserService 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 UserService 实例
        UserService service = new UserService();
        
        // 断言: 实例不为空
        assertThat(service).isNotNull();
    }
}

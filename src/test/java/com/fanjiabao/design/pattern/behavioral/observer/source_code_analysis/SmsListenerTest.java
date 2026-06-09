package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SmsListener 单元测试
 * 测试短信监听器的事件处理
 */
public class SmsListenerTest {

    private SmsListener smsListener;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        smsListener = new SmsListener();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试监听订单创建事件并发送短信")
    void testOnApplicationEvent() {
        // 测试场景: 监听器接收事件并发送短信
        System.setOut(new PrintStream(outContent));
        
        try {
            OrderCreatedEvent event = new OrderCreatedEvent(this, "ORDER_001");
            smsListener.onApplicationEvent(event);
            
            String output = outContent.toString();
            // 断言: 输出包含短信发送消息
            assertThat(output).contains("发送短信");
            assertThat(output).contains("ORDER_001");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试监听器处理多个事件")
    void testMultipleEvents() {
        // 测试场景: 监听器处理多个订单事件
        System.setOut(new PrintStream(outContent));
        
        try {
            smsListener.onApplicationEvent(new OrderCreatedEvent(this, "ORDER_001"));
            smsListener.onApplicationEvent(new OrderCreatedEvent(this, "ORDER_002"));
            smsListener.onApplicationEvent(new OrderCreatedEvent(this, "ORDER_003"));
            
            String output = outContent.toString();
            // 断言: 输出包含多个订单ID
            assertThat(output).contains("ORDER_001");
            assertThat(output).contains("ORDER_002");
            assertThat(output).contains("ORDER_003");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 SmsListener 实现 ApplicationListener 接口")
    void testImplementsApplicationListener() {
        // 测试场景: 验证 SmsListener 实现了正确接口
        boolean isApplicationListener = org.springframework.context.ApplicationListener.class
                .isAssignableFrom(SmsListener.class);
        
        // 断言: 实现了 ApplicationListener 接口
        assertThat(isApplicationListener).isTrue();
    }
}

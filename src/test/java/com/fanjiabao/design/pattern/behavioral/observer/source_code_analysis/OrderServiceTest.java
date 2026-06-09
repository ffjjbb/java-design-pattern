package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OrderService 单元测试
 * 测试订单服务的事件发布功能
 */
public class OrderServiceTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试 OrderService 使用 Mock ApplicationEventPublisher")
    void testCreateOrderWithMockPublisher() {
        // 测试场景: 使用 Mock 的 ApplicationEventPublisher 测试 OrderService
        System.setOut(new PrintStream(outContent));
        
        try {
            org.springframework.context.ApplicationEventPublisher mockPublisher = 
                event -> System.out.println("发布事件: " + event.getClass().getSimpleName());
            
            OrderService orderService = new OrderService(mockPublisher);
            orderService.createOrder();
            
            String output = outContent.toString();
            // 断言: 输出包含订单创建日志
            assertThat(output).contains("创建订单");
            assertThat(output).contains("发布事件");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 OrderService 多次创建订单")
    void testCreateMultipleOrders() {
        // 测试场景: 测试多次创建订单
        System.setOut(new PrintStream(outContent));
        
        try {
            org.springframework.context.ApplicationEventPublisher mockPublisher = 
                event -> System.out.println("事件: " + ((OrderCreatedEvent) event).getOrderId());
            
            OrderService orderService = new OrderService(mockPublisher);
            orderService.createOrder();
            orderService.createOrder();
            orderService.createOrder();
            
            String output = outContent.toString();
            // 断言: 输出包含多次订单创建日志
            assertThat(output).contains("创建订单");
        } finally {
            System.setOut(originalOut);
        }
    }
}

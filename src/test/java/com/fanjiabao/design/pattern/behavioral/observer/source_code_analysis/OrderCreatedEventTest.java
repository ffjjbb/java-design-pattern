package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OrderCreatedEvent 单元测试
 * 测试订单创建事件对象的属性和行为
 */
public class OrderCreatedEventTest {

    @Test
    @DisplayName("测试创建 OrderCreatedEvent 并获取订单ID")
    void testCreateEventAndGetOrderId() {
        // 测试场景: 创建订单事件并验证其属性
        String orderId = "ORDER_001";
        Object source = this;
        
        OrderCreatedEvent event = new OrderCreatedEvent(source, orderId);
        
        // 断言: 验证订单ID正确
        assertThat(event.getOrderId()).isEqualTo(orderId);
    }

    @Test
    @DisplayName("测试事件源对象正确保存")
    void testEventSource() {
        // 测试场景: 验证事件源对象被正确保存
        Object source = new Object();
        OrderCreatedEvent event = new OrderCreatedEvent(source, "ORDER_002");
        
        // 断言: 事件源应该是传入的对象
        assertThat(event.getSource()).isSameAs(source);
    }

    @Test
    @DisplayName("测试不同订单ID的事件不相等")
    void testDifferentOrderIds() {
        // 测试场景: 验证不同订单ID的事件是不同对象
        OrderCreatedEvent event1 = new OrderCreatedEvent(this, "ORDER_001");
        OrderCreatedEvent event2 = new OrderCreatedEvent(this, "ORDER_002");
        
        // 断言: 订单ID不同
        assertThat(event1.getOrderId()).isNotEqualTo(event2.getOrderId());
    }

    @Test
    @DisplayName("测试相同订单ID的事件可以有不同源")
    void testSameOrderIdDifferentSource() {
        // 测试场景: 验证相同订单ID可以来自不同源
        Object source1 = new Object();
        Object source2 = new Object();
        
        OrderCreatedEvent event1 = new OrderCreatedEvent(source1, "ORDER_001");
        OrderCreatedEvent event2 = new OrderCreatedEvent(source2, "ORDER_001");
        
        // 断言: 订单ID相同但源不同
        assertThat(event1.getOrderId()).isEqualTo(event2.getOrderId());
        assertThat(event1.getSource()).isNotSameAs(event2.getSource());
    }

    @Test
    @DisplayName("测试空订单ID")
    void testNullOrderId() {
        // 测试场景: 验证可以为空订单ID创建事件
        OrderCreatedEvent event = new OrderCreatedEvent(this, null);
        
        // 断言: 订单ID为null
        assertThat(event.getOrderId()).isNull();
    }
}

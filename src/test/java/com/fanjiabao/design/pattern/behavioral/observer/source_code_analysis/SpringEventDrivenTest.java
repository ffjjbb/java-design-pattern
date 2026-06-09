package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring 事件驱动观察者模式测试
 * 
 * 测试要点:
 * 1. OrderCreatedEvent 封装事件消息
 * 2. OrderService 发布事件
 * 3. SmsListener 监听事件
 */
class SpringEventDrivenTest {

    @Test
    @DisplayName("OrderCreatedEvent 应继承 ApplicationEvent")
    void orderCreatedEventShouldExtendApplicationEvent() {
        assertThat(OrderCreatedEvent.class.getSuperclass().getSimpleName())
                .as("OrderCreatedEvent 应继承 ApplicationEvent")
                .isEqualTo("ApplicationEvent");
    }

    @Test
    @DisplayName("OrderCreatedEvent 应有 orderId 属性")
    void orderCreatedEventShouldHaveOrderIdField() {
        boolean hasOrderId = false;
        for (var field : OrderCreatedEvent.class.getDeclaredFields()) {
            if (field.getName().equals("orderId")) {
                hasOrderId = true;
                break;
            }
        }
        assertThat(hasOrderId)
                .as("OrderCreatedEvent 应有 orderId 属性")
                .isTrue();
    }

    @Test
    @DisplayName("OrderCreatedEvent 应有 getOrderId 方法")
    void orderCreatedEventShouldHaveGetOrderIdMethod() throws NoSuchMethodException {
        assertThat(OrderCreatedEvent.class.getMethod("getOrderId"))
                .as("OrderCreatedEvent 应有 getOrderId 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("OrderService 应存在")
    void orderServiceShouldExist() {
        assertThat(OrderService.class)
                .as("OrderService 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("OrderService 应有 createOrder 方法")
    void orderServiceShouldHaveCreateOrderMethod() {
        boolean hasCreateOrder = false;
        for (Method method : OrderService.class.getDeclaredMethods()) {
            if (method.getName().equals("createOrder")) {
                hasCreateOrder = true;
                break;
            }
        }
        assertThat(hasCreateOrder)
                .as("OrderService 应有 createOrder 方法")
                .isTrue();
    }

    @Test
    @DisplayName("OrderService 应有 ApplicationEventPublisher 依赖")
    void orderServiceShouldHaveApplicationEventPublisherDependency() {
        boolean hasPublisherField = false;
        for (var field : OrderService.class.getDeclaredFields()) {
            if (field.getType().getSimpleName().contains("ApplicationEventPublisher")) {
                hasPublisherField = true;
                break;
            }
        }
        assertThat(hasPublisherField)
                .as("OrderService 应有 ApplicationEventPublisher 字段")
                .isTrue();
    }

    @Test
    @DisplayName("SmsListener 应存在")
    void smsListenerShouldExist() {
        assertThat(SmsListener.class)
                .as("SmsListener 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("SmsListener 应实现 ApplicationListener 接口")
    void smsListenerShouldImplementApplicationListener() {
        boolean implementsListener = false;
        for (var iface : SmsListener.class.getInterfaces()) {
            if (iface.getSimpleName().equals("ApplicationListener")) {
                implementsListener = true;
                break;
            }
        }
        assertThat(implementsListener)
                .as("SmsListener 应实现 ApplicationListener")
                .isTrue();
    }

    @Test
    @DisplayName("SmsListener 应有 onApplicationEvent 方法")
    void smsListenerShouldHaveOnApplicationEventMethod() throws NoSuchMethodException {
        assertThat(SmsListener.class.getMethod("onApplicationEvent", OrderCreatedEvent.class))
                .as("SmsListener 应有 onApplicationEvent 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("SpringEventDrivenAnalysis 应有 main 方法")
    void springEventDrivenAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = SpringEventDrivenAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = SpringEventDrivenAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("SpringEventDrivenAnalysis 应有 main 方法")
                .isNotNull();
    }
}

package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.springframework.context.ApplicationEvent;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 12:00
 * @description: 事件对象
 * 在观察者模式中, 它相当于 "通知消息", 普通观察者模式中可能只是传一个 String message,
 * Spring 中把这个 message 封装成了 ApplicationEvent。
 */
public class OrderCreatedEvent extends ApplicationEvent {

    private final String orderId;

    /**
     * @param source 事件源，表示是谁发布的事件
     * @param orderId 订单编号
     */
    public OrderCreatedEvent(Object source, String orderId) {
        super(source);
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}

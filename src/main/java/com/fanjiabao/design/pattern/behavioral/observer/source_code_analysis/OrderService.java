package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 12:00
 * @description: 订单服务(发布者)
 * 在观察者模式中, 它相当于 Subject / 被观察者 / 事件发布者。但是它不直接依赖 SmsListener。它只负责发布 OrderCreatedEvent。
 * 好处：
 *  OrderService 不知道谁会监听这个事件
 *  后续新增 EmailListener, StockListener, LogListener, 不需要修改 OrderService
 *  发布者和监听者解耦
 */
@Component
public class OrderService {

    /**
     * ApplicationEventPublisher 是 Spring 提供的事件发布器接口。
     * 这里注入的实际对象就是当前 Spring 容器 AnnotationConfigApplicationContext。
     * 因为 AnnotationConfigApplicationContext extends AbstractApplicationContext implements ApplicationEventPublisher
     */
    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 发布订单创建事件。
     * 这里不是直接调用: smsListener.onApplicationEvent(...)
     * 而是交给 Spring: publisher.publishEvent(...)
     * Spring 内部会找到所有监听 OrderCreatedEvent 的监听器。
     */
    public void createOrder() {
        System.out.println("1.创建订单...");
        publisher.publishEvent(new OrderCreatedEvent(this, "ORDER_001"));
    }

}

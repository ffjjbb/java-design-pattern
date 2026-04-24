package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 12:01
 * @description: 监听器(Observer)
 * ApplicationListener<OrderCreatedEvent>: 只关心 OrderCreatedEvent 类型的事件。
 */
@Component
public class SmsListener implements ApplicationListener<OrderCreatedEvent> {

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        System.out.println("2.发送短信: " + event.getOrderId());
    }

}

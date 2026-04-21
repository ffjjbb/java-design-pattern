package com.fanjiabao.design.pattern.behavioral.command;

import java.util.Map;
import java.util.Set;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 9:22
 * @description: 具体的命令类
 */
public class OrderCommand implements Command {

    // 接受者对象
    private SeniorChef receiver;

    private Order order;

    public OrderCommand(SeniorChef receiver, Order order) {
        this.receiver = receiver;
        this.order = order;
    }

    @Override
    public void execute() {
        System.out.println(order.getDiningTable() + " 桌的订单: ");
        Map<String, Integer> foodDir = order.getFoodDir();
        Set<String> keys = foodDir.keySet();
        for (String foodName : keys) {
            // do something
            receiver.makeFood(foodName, foodDir.get(foodName));
        }
        System.out.println(order.getDiningTable() + " 桌的饭准备完毕!");
    }
}

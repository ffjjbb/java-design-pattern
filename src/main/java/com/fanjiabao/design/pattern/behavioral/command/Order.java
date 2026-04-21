package com.fanjiabao.design.pattern.behavioral.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 9:22
 * @description: 订单类
 */
public class Order {

    // 桌号
    private int diningTable;

    private Map<String, Integer> foodDir = new HashMap<>();

    public int getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(int diningTable) {
        this.diningTable = diningTable;
    }

    public Map<String, Integer> getFoodDir() {
        return foodDir;
    }

    public void setFood(String name, int num) {
        foodDir.put(name, num);
    }
}

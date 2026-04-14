package com.fanjiabao.design.pattern.structural.proxy;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/14 9:35
 * @description: 火车站
 */
public class TrainStation implements SellTickets {

    public void sell() {
        System.out.println("火车站卖票");
    }

}

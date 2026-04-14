package com.fanjiabao.design.pattern.structural.proxy.static_proxy;

import com.fanjiabao.design.pattern.structural.proxy.SellTickets;
import com.fanjiabao.design.pattern.structural.proxy.TrainStation;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/14 9:36
 * @description: 代售点
 */
public class ProxyPoint implements SellTickets {

    private TrainStation station = new TrainStation();

    public void sell() {
        System.out.println("代理点收取一些服务费用");
        station.sell();
    }

}

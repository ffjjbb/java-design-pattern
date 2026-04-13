package com.fanjiabao.design.pattern.creator.builder;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 16:48
 * @description: 构建ofo单车
 */
public class OfoBuilder extends Builder {

    public void buildFrame() {
        bike.setFrame("铝合金车架");
    }

    public void buildSeat() {
        bike.setSeat("橡胶车座");
    }

    public Bike createBike() {
        return bike;
    }
}

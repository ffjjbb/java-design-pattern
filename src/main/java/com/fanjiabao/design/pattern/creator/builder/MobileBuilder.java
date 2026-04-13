package com.fanjiabao.design.pattern.creator.builder;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 16:47
 * @description: 构建摩拜单车对象
 */
public class MobileBuilder extends Builder {

    public void buildFrame() {
        bike.setFrame("碳纤维车架");
    }

    public void buildSeat() {
        bike.setSeat("真皮车座");
    }

    public Bike createBike() {
        return bike;
    }
}

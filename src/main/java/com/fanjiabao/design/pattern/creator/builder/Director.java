package com.fanjiabao.design.pattern.creator.builder;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 16:49
 * @description: 指挥者类
 */
public class Director {

    private Builder mBuilder;

    public Director(Builder builder) {
        mBuilder = builder;
    }

    public Bike construct() {
        mBuilder.buildFrame();
        mBuilder.buildSeat();
        return mBuilder.createBike();
    }

}

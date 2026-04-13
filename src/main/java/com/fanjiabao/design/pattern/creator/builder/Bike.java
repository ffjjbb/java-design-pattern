package com.fanjiabao.design.pattern.creator.builder;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 16:36
 * @description: 自行车产品类
 */
public class Bike {

    // 车架
    private String frame;

    // 车座
    private String seat;

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

}

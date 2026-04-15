package com.fanjiabao.design.pattern.structural.adapter;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:03
 * @description: 电脑
 */
public class Computer {

    public String readSD(SDCard sdCard) {
        if(sdCard == null) {
            throw new NullPointerException("sd card null");
        }
        return sdCard.readSD();
    }

}

package com.fanjiabao.design.pattern.structural.adapter;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:03
 * @description: SD卡实现类
 */
public class SDCardImpl implements SDCard {

    @Override
    public String readSD() {
        return "sd card read a msg: Hello Fan!";
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("sd card write msg: " + msg);
    }

}

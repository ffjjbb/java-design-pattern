package com.fanjiabao.design.pattern.structural.adapter;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:04
 * @description: TF卡实现类
 */
public class TFCardImpl implements TFCard {

    @Override
    public String readTF() {
        return "tf card read msg: Hello king";
    }

    @Override
    public void writeTF(String msg) {
        System.out.println("tf card write a msg: " + msg);
    }

}

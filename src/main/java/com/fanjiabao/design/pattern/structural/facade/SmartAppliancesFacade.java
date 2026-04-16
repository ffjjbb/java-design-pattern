package com.fanjiabao.design.pattern.structural.facade;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 16:38
 * @description: 智能音箱
 */
public class SmartAppliancesFacade {

    private Light light;

    private TV tv;

    private AirCondition airCondition;

    public SmartAppliancesFacade() {
        light = new Light();
        tv = new TV();
        airCondition = new AirCondition();
    }

    public void say(String message) {
        if(message.contains("打开")) {
            on();
        } else if(message.contains("关闭")) {
            off();
        } else {
            System.out.println("我还听不懂你说的！！！");
        }
    }

    private void on() {
        System.out.println("起床了");
        light.on();
        tv.on();
        airCondition.on();
    }

    private void off() {
        System.out.println("睡觉了");
        light.off();
        tv.off();
        airCondition.off();
    }

}

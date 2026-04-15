package com.fanjiabao.design.pattern.structural.adapter.object_adapter;

import com.fanjiabao.design.pattern.structural.adapter.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:25
 * @description:
 * 结构型模式.适配器模式.对象适配器模式:
 *  对象适配器模式可釆用将现有组件库中已经实现的组件引入适配器类中, 该类同时实现当前系统的业务接口。
 */
public class ObjectAdapter {

    /**
     * 应用场景:
     *  以前开发的系统存在满足新系统功能需求的类, 但其接口同新系统的接口不一致
     *  使用第三方提供的组件, 但组件接口定义和自己要求的接口定义不同
     * <p>
     * 注:
     *  还有一个适配器模式是接口适配器模式, 当不希望实现一个接口中所有的方法时, 可以创建一个抽象类 Adapter, 实现所有方法,
     *  而此时我们只需要继承该抽象类即可, 如: java.awt.event.MouseAdapter
     */
    public static void main(String[] args) {
        Computer computer = new Computer();
        SDCard sdCard = new SDCardImpl();
        System.out.println(computer.readSD(sdCard));

        System.out.println("------------");

        TFCard tfCard = new TFCardImpl();
        SDObjectAdapterTF adapter = new SDObjectAdapterTF(tfCard);
        System.out.println(computer.readSD(adapter));
    }

}

package com.fanjiabao.design.pattern.structural.adapter.class_adapter;

import com.fanjiabao.design.pattern.structural.adapter.Computer;
import com.fanjiabao.design.pattern.structural.adapter.SDCard;
import com.fanjiabao.design.pattern.structural.adapter.SDCardImpl;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 9:57
 * @description:
 * 结构型模式.适配器模式:
 *  将一个类的接口转换成客户希望的另外一个接口, 使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
 *  适配器模式分为类适配器模式和对象适配器模式, 前者类之间的耦合度比后者高, 且要求程序员了解现有组件库中的相关组件的内部结构, 所以应用相对较少些。
 * <p>
 * 类适配器模式:
 *  实现方式：定义一个适配器类来实现当前系统的业务接口, 同时又继承现有组件库中已经存在的组件。
 */
public class ClassAdapter {

    /**
     * 适配器模式(Adapter)主要角色:
     *  目标(Target)接口: 当前系统业务所期待的接口, 它可以是抽象类或接口。
     *  适配者(Adaptee)类: 它是被访问和适配的现存组件库中的组件接口。
     *  适配器(Adapter)类: 它是一个转换器, 通过继承或引用适配者的对象, 把适配者接口转换成目标接口, 让客户按目标接口的格式访问适配者。
     * <p>
     * 类适配器模式违背了合成复用原则(尽量使用 "组合/聚合" 来复用代码, 而不是使用继承)
     * 类适配器模式只有在 "客户端已经定义好接口(Target)" 的情况下才有意义, 因为它的作用是让不兼容的类去实现这个接口;
     * 如果没有接口规范, 类适配器就不可用。
     */
    public static void main(String[] args) {
        Computer computer = new Computer();
        SDCard sdCard = new SDCardImpl();
        System.out.println(computer.readSD(sdCard));

        System.out.println("------------");

        SDClassAdapterTF adapter = new SDClassAdapterTF();
        System.out.println(computer.readSD(adapter));
    }

}

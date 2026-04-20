package com.fanjiabao.design.pattern.behavioral.template_method;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 15:47
 * @description: 炒菜心
 */
public class ConcreteClass_CaiXin extends AbstractClass {

    public void pourVegetable() {
        System.out.println("搞点菜心");
    }

    public void pourSauce() {
        System.out.println("下点蒜蓉");
    }

}

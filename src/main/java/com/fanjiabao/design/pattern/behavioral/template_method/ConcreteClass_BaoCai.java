package com.fanjiabao.design.pattern.behavioral.template_method;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 15:46
 * @description: 炒包菜
 */
public class ConcreteClass_BaoCai extends AbstractClass {

    public void pourVegetable() {
        System.out.println("整点包菜");
    }

    public void pourSauce() {
        System.out.println("上点辣椒");
    }

}


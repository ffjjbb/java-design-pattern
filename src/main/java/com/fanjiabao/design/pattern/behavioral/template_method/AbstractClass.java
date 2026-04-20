package com.fanjiabao.design.pattern.behavioral.template_method;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 15:44
 * @description: 抽象类(定义模板方法和基本方法)
 */
public abstract class AbstractClass {

    // 模板方法定义
    public final void cookProcess() {
        pourOil();
        heatOil();
        pourVegetable();
        pourSauce();
        fry();
    }

    public void pourOil() {
        System.out.println("倒油");
    }

    public void heatOil() {
        System.out.println("热油");
    }

    public abstract void pourVegetable();

    public abstract void pourSauce();

    public void fry(){
        System.out.println("炒啊炒啊炒到熟啊");
    }

}

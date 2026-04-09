package com.fanjiabao.design.pattern.creator.factory.abstract_factory;

import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.Dessert;
import com.fanjiabao.design.pattern.creator.factory.LatteCoffee;
import com.fanjiabao.design.pattern.creator.factory.Tiramisu;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 9:07
 * @description: 意大利风味工厂
 */
public class ItalyDessertFactory implements DessertFactory {

    public Coffee createCoffee() {
        return new LatteCoffee();
    }

    public Dessert createDessert() {
        return new Tiramisu();
    }
}

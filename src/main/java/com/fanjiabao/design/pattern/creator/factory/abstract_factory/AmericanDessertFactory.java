package com.fanjiabao.design.pattern.creator.factory.abstract_factory;

import com.fanjiabao.design.pattern.creator.factory.AmericanCoffee;
import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.Dessert;
import com.fanjiabao.design.pattern.creator.factory.MatchaMousse;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 9:05
 * @description: 美式风味工厂
 */
public class AmericanDessertFactory implements DessertFactory {

    public Coffee createCoffee() {
        return new AmericanCoffee();
    }

    public Dessert createDessert() {
        return new MatchaMousse();
    }
}

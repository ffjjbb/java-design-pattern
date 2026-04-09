package com.fanjiabao.design.pattern.creator.factory.method;

import com.fanjiabao.design.pattern.creator.factory.AmericanCoffee;
import com.fanjiabao.design.pattern.creator.factory.Coffee;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/8 11:52
 * @description: 美式咖啡的具体抽象工厂
 */
public class AmericanCoffeeFactory implements CoffeeMethodFactory {

    public Coffee createCoffee() {
        return new AmericanCoffee();
    }

}

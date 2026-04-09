package com.fanjiabao.design.pattern.creator.factory.method;

import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.LatteCoffee;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/8 11:52
 * @description: 拿铁咖啡的具体抽象工厂
 */
public class LatteCoffeeFactory implements CoffeeMethodFactory {

    public Coffee createCoffee() {
        return new LatteCoffee();
    }

}

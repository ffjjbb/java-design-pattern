package com.fanjiabao.design.pattern.creator.factory.abstract_factory;

import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.Dessert;

public interface DessertFactory {

    // 生产咖啡的功能
    Coffee createCoffee();

    // 生产甜品的功能
    Dessert createDessert();

}

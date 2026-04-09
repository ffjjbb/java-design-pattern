package com.fanjiabao.design.pattern.creator.factory.abstract_factory;

import com.fanjiabao.design.pattern.creator.factory.Coffee;
import com.fanjiabao.design.pattern.creator.factory.Dessert;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/8 12:12
 * @description:
 * 工厂模式.抽象工厂模式:
 *  是一种为访问类提供一个创建一组相关或相互依赖对象的接口, 且访问类无须指定所要产品的具体类就能得到同族的不同等级的产品的模式结构
 *  抽象工厂模式是工厂方法模式的升级版本, 工厂方法模式只生产一个等级的产品, 而抽象工厂模式可生产多个等级的产品
 */
public class AbstractFactory {

    /**
     * 优点: 当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。
     * 缺点: 当产品族中需要增加一个新的产品时，所有的工厂类都需要进行修改。
     * ---------------------------
     * 抽象工厂模式的主要角色如下：
     *  抽象工厂(Abstract Factory): 提供了创建产品的接口, 它包含多个创建产品的方法, 可以创建多个不同等级的产品
     *  具体工厂(Concrete Factory): 主要是实现抽象工厂中的多个抽象方法, 完成具体产品的创建
     *  抽象产品(Product): 定义了产品的规范, 描述了产品的主要特性和功能, 抽象工厂模式有多个抽象产品
     *  具体产品(ConcreteProduct): 实现了抽象产品角色所定义的接口, 由具体工厂来创建, 它同具体工厂之间是多对一的关系
     */
    public static void main(String[] args) {
        AmericanDessertFactory factory = new AmericanDessertFactory();
        Coffee coffee = factory.createCoffee();
        Dessert dessert = factory.createDessert();
        System.out.println(coffee.getName());
        dessert.show();
    }

    /**
     * 使用场景:
     *  当需要创建的对象是一系列相互关联或相互依赖的产品族时, 如电器工厂中的电视机, 洗衣机, 空调等
     *  系统中有多个产品族, 但每次只使用其中的某一族产品。如有人只喜欢穿某一个品牌的衣服和鞋
     *  系统中提供了产品的类库, 且所有产品的接口相同, 客户端不依赖产品实例的创建细节和内部结构
     *  如: 订单有景区门票,酒店,导游,旅游线路规划,餐饮等类型, 选择某一类订单实现, 取消创建支付等就会被统一替换, 这样新增产品类时无需修原代码流程
     */
    public void usageScenarios() {}

}

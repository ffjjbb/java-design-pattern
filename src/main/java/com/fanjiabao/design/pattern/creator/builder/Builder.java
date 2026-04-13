package com.fanjiabao.design.pattern.creator.builder;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 16:38
 * @description: 抽象建造者类
 */
public abstract class Builder {

    protected Bike bike = new Bike();

    public abstract void buildFrame();

    public abstract void buildSeat();

    public abstract Bike createBike();

    /**
     * 示例是 Builder 模式的常规用法, 指挥者类 Director 在建造者模式中具有很重要的作用, 它用于指导具体构建者如何构建产
     * 品, 控制调用先后次序, 并向调用者返回完整的产品类, 但是有些情况下需要简化系统结构, 可以把指挥者类和抽象建造者进行结合。
     * 这样做确实简化了系统结构, 但同时也加重了抽象建造者类的职责, 也不是太符合单一职责原则, 如果 construct() 过于复杂,
     * 建议还是封装到 Director 中。
     */
    public Bike construct() {
        this.buildFrame();
        this.buildSeat();
        return this.createBike();
    }

}

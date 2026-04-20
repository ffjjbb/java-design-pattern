package com.fanjiabao.design.pattern.behavioral.strategy;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 17:15
 * @description: (环境类
 */
public class SalesMan {

    private Strategy strategy;

    public SalesMan(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void salesManShow() {
        strategy.show();
    }
}

package com.fanjiabao.design.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 11:42
 * @description: 具体主题角色类
 */
public class SubscriptionSubject implements Subject {

    // 存储多个观察者对象
    private List<Observer> weiXinUserList = new ArrayList<>();

    public void attach(Observer observer) {
        weiXinUserList.add(observer);
    }

    public void detach(Observer observer) {
        weiXinUserList.remove(observer);
    }

    public void notify(String message) {
        for (Observer observer : weiXinUserList) {
            observer.update(message);
        }
    }
}


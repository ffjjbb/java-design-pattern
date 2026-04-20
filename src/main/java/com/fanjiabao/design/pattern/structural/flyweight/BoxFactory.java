package com.fanjiabao.design.pattern.structural.flyweight;

import java.util.HashMap;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 10:42
 * @description: 工厂类, 将该类设计为单例
 */
public class BoxFactory {

    private HashMap<String, AbstractBox> map;

    private BoxFactory() {
        map = new HashMap<>();
        map.put("I", new IBox());
        map.put("L", new LBox());
        map.put("O", new OBox());
    }

    public static BoxFactory getInstance() {
        return factory;
    }

    private static BoxFactory factory = new BoxFactory();

    public AbstractBox getShape(String name) {
        return map.get(name);
    }

}

package com.fanjiabao.design.pattern.structural.composite;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 9:30
 * @description: 菜单组件(属于抽象根节点)
 */
public abstract class MenuComponent {

    protected String name;

    protected int level;

    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public MenuComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public abstract void print();
}

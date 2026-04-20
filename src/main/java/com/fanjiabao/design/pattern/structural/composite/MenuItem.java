package com.fanjiabao.design.pattern.structural.composite;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 9:31
 * @description: 菜单项类(属于叶子节点)
 */
public class MenuItem extends MenuComponent {

    public MenuItem(String name,int level) {
        this.name = name;
        this.level = level;
    }

    public void print() {
        for(int i = 0; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(name);
    }
}

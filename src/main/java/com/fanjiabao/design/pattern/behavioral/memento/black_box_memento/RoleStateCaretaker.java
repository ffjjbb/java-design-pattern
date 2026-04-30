package com.fanjiabao.design.pattern.behavioral.memento.black_box_memento;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 10:32
 * @description: 备忘录对象管理对象
 */
public class RoleStateCaretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}

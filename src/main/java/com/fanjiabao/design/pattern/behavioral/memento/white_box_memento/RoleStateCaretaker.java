package com.fanjiabao.design.pattern.behavioral.memento.white_box_memento;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 10:33
 * @description: 备忘录对象管理对象
 */
public class RoleStateCaretaker {

    private RoleStateMemento roleStateMemento;

    public RoleStateMemento getRoleStateMemento() {
        return roleStateMemento;
    }

    public void setRoleStateMemento(RoleStateMemento roleStateMemento) {
        this.roleStateMemento = roleStateMemento;
    }
}


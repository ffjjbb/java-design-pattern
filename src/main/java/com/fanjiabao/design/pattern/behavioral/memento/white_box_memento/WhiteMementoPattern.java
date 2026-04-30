package com.fanjiabao.design.pattern.behavioral.memento.white_box_memento;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:17
 * @description: 描述
 */
public class WhiteMementoPattern {

    public static void main(String[] args) {
        System.out.println("---------------大战boos前-----------------");
        // 创建游戏角色对象
        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole gameRole =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole();
        // 初始化状态操作
        gameRole.initState();
        gameRole.stateDisplay();

        // 将该游戏角色内部状态进行备份
        // 创建管理者对象
        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.RoleStateCaretaker roleStateCaretaker =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.RoleStateCaretaker();
        roleStateCaretaker.setRoleStateMemento(gameRole.saveState());

        System.out.println("---------------大战boos后-----------------");
        // 损耗严重
        gameRole.fight();
        gameRole.stateDisplay();

        System.out.println("---------------恢复之前的状态-----------------");
        gameRole.recoverState(roleStateCaretaker.getRoleStateMemento());
        gameRole.stateDisplay();
    }

}

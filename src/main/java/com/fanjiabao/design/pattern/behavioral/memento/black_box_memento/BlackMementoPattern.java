package com.fanjiabao.design.pattern.behavioral.memento.black_box_memento;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:16
 * @description: 黑箱
 */
public class BlackMementoPattern {


    public static void main(String[] args) {
        System.out.println("---------------大战boos前-----------------");
        // 创建游戏角色对象
        GameRole gameRole = new  GameRole();
        // 初始化状态操作
        gameRole.initState();
        gameRole.stateDisplay();

        // 将该游戏角色内部状态进行备份
        // 创建管理者对象
        RoleStateCaretaker roleStateCaretaker = new RoleStateCaretaker();
        roleStateCaretaker.setMemento(gameRole.saveState());

        System.out.println("---------------大战boos后-----------------");
        // 损耗严重
        gameRole.fight();
        gameRole.stateDisplay();

        System.out.println("---------------恢复之前的状态-----------------");
        gameRole.recoverState(roleStateCaretaker.getMemento());
        gameRole.stateDisplay();
    }

}

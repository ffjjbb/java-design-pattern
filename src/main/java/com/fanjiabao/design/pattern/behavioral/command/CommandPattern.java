package com.fanjiabao.design.pattern.behavioral.command;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 9:15
 * @description: 行为型模式.命令模式:
 * 把 "请求" 封装成对象, 从而让你可以参数化,排队,记录,撤销请求, 使发出请求的责任和执行请求的责任分割开。
 */
public class CommandPattern {

    /**
     * 命令模式包含以下主要角色:
     * 抽象命令类(Command)角色: 定义命令的接口, 声明执行的方法。
     * 具体命令(Concrete  Command)角色: 具体的命令, 实现命令接口; 通常会持有接收者, 并调用接收者的功能来完成命令要执行的操作。
     * 实现者/接收者(Receiver)角色: 接收者, 真正执行命令的对象。任何类都可能成为一个接收者, 只要它能够实现命令要求实现的相应功能。
     * 调用者/请求者(Invoker)角色: 要求命令对象执行请求, 通常会持有命令对象, 可以持有很多的命令对象。这个是客户端真正触发命令并要
     * 求命令执行相应操作的地方, 也就是说相当于使用命令对象的入口。
     * <p>
     * 优:
     *  - 降低系统的耦合度。命令模式能将调用操作的对象与实现该操作的对象解耦。
     *  - 增加或删除命令非常方便。采用命令模式增加与删除命令不会影响其他类, 它满足 "开闭原则" , 对扩展比较灵活。
     *  - 可以实现宏命令。命令模式可以与组合模式结合, 将多个命令装配成一个组合命令, 即宏命令。
     *  - 方便实现 Undo 和 Redo 操作。命令模式可以与后面介绍的备忘录模式结合, 实现命令的撤销与恢复。
     * 缺:
     *  使用命令模式可能会导致某些系统有过多的具体命令类。
     *  系统结构更加复杂。
     */
    public static void main(String[] args) {
        Order order1 = new Order();
        order1.setDiningTable(1);
        order1.setFood("西红柿鸡蛋面", 1);
        order1.setFood("小杯可乐", 2);
        Order order2 = new Order();
        order2.setDiningTable(2);
        order2.setFood("尖椒肉丝盖饭", 1);
        order2.setFood("小杯雪碧", 1);

        SeniorChef receiver = new SeniorChef();
        // 命令对象
        OrderCommand cmd1 = new OrderCommand(receiver, order1);
        OrderCommand cmd2 = new OrderCommand(receiver, order2);

        // 创建调用者(服务员对象)
        Waiter invoke = new Waiter();
        invoke.setCommand(cmd1);
        invoke.setCommand(cmd2);

        // 让服务员发起命令
        invoke.orderUp();
    }

    /**
     * 使用场景:
     *  系统需要将请求调用者和请求接收者解耦, 使得调用者和接收者不直接交互。
     *  系统需要在不同的时间指定请求, 将请求排队和执行请求。
     *  系统需要支持命令的撤销(Undo)操作和恢复(Redo)操作。
     */
    public void usageScenarios() {}

}

package com.fanjiabao.design.pattern.behavioral.command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 9:24
 * @description: 服务员类(属于请求者角色)
 */
public class Waiter {

    // 命令对象
    private List<Command> commands = new ArrayList<>();

    public void setCommand(Command cmd) {
        commands.add(cmd);
    }

    public void orderUp() {
        System.out.println("服务员: 后厨, 新订单来了");
        for (Command command : commands) {
            if(command != null) {
                command.execute();
            }
        }
    }
}

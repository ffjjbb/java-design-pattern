package com.fanjiabao.design.pattern.behavioral.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 命令模式单元测试
 */
class CommandTest {

    @Test
    @DisplayName("命令: OrderCommand 应实现 Command 接口")
    void orderCommand_shouldImplementCommand() {
        SeniorChef chef = new SeniorChef();
        Order order = new Order();
        order.setDiningTable(1);
        OrderCommand cmd = new OrderCommand(chef, order);
        assertThat(cmd).isInstanceOf(Command.class);
    }

    @Test
    @DisplayName("命令: Order 应正确存储餐桌号和食物")
    void order_shouldStoreTableAndFood() {
        Order order = new Order();
        order.setDiningTable(3);
        order.setFood("蛋炒饭", 2);
        order.setFood("可乐", 1);

        assertThat(order.getDiningTable()).isEqualTo(3);
        assertThat(order.getFoodDir()).hasSize(2);
        assertThat(order.getFoodDir()).containsKey("蛋炒饭");
    }

    @Test
    @DisplayName("命令: Waiter 应管理多个命令")
    void waiter_shouldManageMultipleCommands() {
        Waiter waiter = new Waiter();
        SeniorChef chef = new SeniorChef();
        Order order1 = new Order();
        order1.setDiningTable(1);
        order1.setFood("面", 1);

        Order order2 = new Order();
        order2.setDiningTable(2);
        order2.setFood("饭", 1);

        waiter.setCommand(new OrderCommand(chef, order1));
        waiter.setCommand(new OrderCommand(chef, order2));

        waiter.orderUp(); // 不抛异常即为通过
    }

    @Test
    @DisplayName("命令: 单命令执行流程应正常")
    void command_shouldExecuteSingleOrder() {
        SeniorChef chef = new SeniorChef();
        Order order = new Order();
        order.setDiningTable(5);
        order.setFood("宫保鸡丁", 1);

        Waiter waiter = new Waiter();
        waiter.setCommand(new OrderCommand(chef, order));
        waiter.orderUp();
    }

    @Test
    @DisplayName("命令: OrderCommand execute 应调用 SeniorChef makeFood")
    void orderCommand_shouldCallChefMakeFood() {
        Order order = new Order();
        order.setDiningTable(1);
        order.setFood("测试菜", 3);

        SeniorChef chef = new SeniorChef();
        OrderCommand cmd = new OrderCommand(chef, order);

        cmd.execute(); // 不抛异常即为通过
    }
}

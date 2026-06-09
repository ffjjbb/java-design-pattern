package com.fanjiabao.design.pattern.behavioral.responsibility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 责任链模式单元测试
 */
class ResponsibilityTest {

    @Test
    @DisplayName("责任链: LeaveRequest 应正确存储属性")
    void leaveRequest_shouldStoreProperties() {
        LeaveRequest request = new LeaveRequest("张三", 3, "家中有事");
        assertThat(request.getName()).isEqualTo("张三");
        assertThat(request.getNum()).isEqualTo(3);
        assertThat(request.getContent()).isEqualTo("家中有事");
    }

    @Test
    @DisplayName("责任链: GroupLeader 可处理 1 天请假")
    void groupLeader_shouldHandleOneDayLeave() {
        GroupLeader leader = new GroupLeader();
        leader.submit(new LeaveRequest("张三", 1, "事假"));
    }

    @Test
    @DisplayName("责任链: 完整责任链应能处理 2 天请假(小组长→经理)")
    void chain_shouldHandleTwoDaysLeave() {
        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        GeneralManager generalManager = new GeneralManager();

        groupLeader.setNextHandler(manager);
        manager.setNextHandler(generalManager);

        groupLeader.submit(new LeaveRequest("吕阳", 2, "被知见障蒙蔽了"));
    }

    @Test
    @DisplayName("责任链: 完整责任链应能处理 5 天请假(小组长→经理→总经理)")
    void chain_shouldHandleFiveDaysLeave() {
        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        GeneralManager generalManager = new GeneralManager();

        groupLeader.setNextHandler(manager);
        manager.setNextHandler(generalManager);

        groupLeader.submit(new LeaveRequest("刘长", 5, "旅游"));
    }

    @Test
    @DisplayName("责任链: Handler 应能设置后继处理器")
    void handler_shouldSetNextHandler() {
        GroupLeader leader = new GroupLeader();
        Manager manager = new Manager();
        leader.setNextHandler(manager);
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("责任链: Handler 常量应正确")
    void handler_constantsShouldBeCorrect() {
        assertThat(Handler.NUM_ONE).isEqualTo(1);
        assertThat(Handler.NUM_THREE).isEqualTo(3);
        assertThat(Handler.NUM_SEVEN).isEqualTo(7);
    }
}

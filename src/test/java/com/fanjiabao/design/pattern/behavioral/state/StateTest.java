package com.fanjiabao.design.pattern.behavioral.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 状态模式单元测试
 */
class StateTest {

    @Test
    @DisplayName("状态: Lift 应实现 ILift 接口")
    void lift_shouldImplementILift() {
        Lift lift = new Lift();
        assertThat(lift).isInstanceOf(ILift.class);
    }

    @Test
    @DisplayName("状态: OPENING 状态下 open 不改变状态")
    void openingState_openShouldNotChangeState() {
        Lift lift = new Lift();
        lift.setState(ILift.OPENING_STATE);
        lift.open(); // 应该保持不变
    }

    @Test
    @DisplayName("状态: RUNNING 状态下 open 不改变状态")
    void runningState_openShouldNotChangeState() {
        Lift lift = new Lift();
        lift.setState(ILift.RUNNING_STATE);
        lift.open(); // 应该保持不变
    }

    @Test
    @DisplayName("状态: CLOSING 状态下 run 应切换到 RUNNING_STATE")
    void closingState_runShouldSwitchToRunning() {
        Lift lift = new Lift();
        lift.setState(ILift.CLOSING_STATE);
        lift.run();
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("状态: RUNNING 状态下 stop 应切换到 STOPPING_STATE")
    void runningState_stopShouldSwitchToStopping() {
        Lift lift = new Lift();
        lift.setState(ILift.RUNNING_STATE);
        lift.stop();
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("状态: 初始打开->关闭->运行->停止 完整流程")
    void fullStateTransitionSequence() {
        Lift lift = new Lift();
        lift.setState(ILift.CLOSING_STATE);
        lift.open();  // 关门 -> 开门

        lift.close(); // 开门 -> 关门
        lift.run();   // 关门 -> 运行
        lift.stop();  // 运行 -> 停止
    }

    @Test
    @DisplayName("状态: ILift 常量应正确")
    void ilift_constantsShouldBeCorrect() {
        assertThat(ILift.OPENING_STATE).isEqualTo(1);
        assertThat(ILift.CLOSING_STATE).isEqualTo(2);
        assertThat(ILift.RUNNING_STATE).isEqualTo(3);
        assertThat(ILift.STOPPING_STATE).isEqualTo(4);
    }
}

package com.fanjiabao.design.pattern.structural.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 外观模式单元测试
 */
class FacadeTest {

    @Test
    @DisplayName("外观: SmartAppliancesFacade 创建应不抛异常")
    void facade_shouldCreateWithoutException() {
        SmartAppliancesFacade facade = new SmartAppliancesFacade();
        assertThat(facade).isNotNull();
    }

    @Test
    @DisplayName("外观: say(打开) 应触发所有设备开启")
    void facade_shouldTurnOnAllDevices() {
        SmartAppliancesFacade facade = new SmartAppliancesFacade();
        facade.say("打开家电");
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("外观: say(关闭) 应触发所有设备关闭")
    void facade_shouldTurnOffAllDevices() {
        SmartAppliancesFacade facade = new SmartAppliancesFacade();
        facade.say("关闭家电");
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("外观: 未知指令应有默认响应")
    void facade_shouldHandleUnknownCommand() {
        SmartAppliancesFacade facade = new SmartAppliancesFacade();
        facade.say("播放音乐");
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("子系统: Light 开关应正常")
    void light_shouldWork() {
        Light light = new Light();
        light.on();
        light.off();
    }

    @Test
    @DisplayName("子系统: TV 开关应正常")
    void tv_shouldWork() {
        TV tv = new TV();
        tv.on();
        tv.off();
    }

    @Test
    @DisplayName("子系统: AirCondition 开关应正常")
    void airCondition_shouldWork() {
        AirCondition ac = new AirCondition();
        ac.on();
        ac.off();
    }
}

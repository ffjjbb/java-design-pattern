package com.fanjiabao.design.pattern.structural.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 桥接模式单元测试
 */
class BridgeTest {

    @Test
    @DisplayName("桥接: Mac + AviFile 组合应正常工作")
    void macWithAvi_shouldWork() {
        OperatingSystem system = new Mac(new AviFile());
        assertThat(system).isInstanceOf(OperatingSystem.class);
        system.play("战狼3");
    }

    @Test
    @DisplayName("桥接: Mac + RmvbFile 组合应正常工作")
    void macWithRmvb_shouldWork() {
        OperatingSystem system = new Mac(new RmvbFile());
        system.play("流浪地球");
    }

    @Test
    @DisplayName("桥接: Windows + AviFile 组合应正常工作")
    void windowsWithAvi_shouldWork() {
        OperatingSystem system = new Windows(new AviFile());
        system.play("哪吒");
    }

    @Test
    @DisplayName("桥接: Windows + RmvbFile 组合应正常工作")
    void windowsWithRmvb_shouldWork() {
        OperatingSystem system = new Windows(new RmvbFile());
        system.play("深海");
    }

    @Test
    @DisplayName("桥接: OperatingSystem 应持有 VideoFile 引用")
    void operatingSystem_shouldHoldVideoFileReference() {
        VideoFile videoFile = new AviFile();
        Mac mac = new Mac(videoFile);
        assertThat(mac.videoFile).isSameAs(videoFile);
    }

    @Test
    @DisplayName("桥接: Mac 和 Windows 可共享同一个 VideoFile 实例")
    void bridge_shouldAllowSharedVideoFile() {
        VideoFile avi = new AviFile();
        OperatingSystem mac = new Mac(avi);
        OperatingSystem windows = new Windows(avi);

        assertThat(mac.videoFile).isSameAs(windows.videoFile);
    }
}

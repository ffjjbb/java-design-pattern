package com.fanjiabao.design.pattern.structural.adapter;

import com.fanjiabao.design.pattern.structural.adapter.class_adapter.SDClassAdapterTF;
import com.fanjiabao.design.pattern.structural.adapter.object_adapter.SDObjectAdapterTF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 适配器模式单元测试
 */
class AdapterTest {

    // ===================== SDCard 直接读取 =====================

    @Test
    @DisplayName("SDCard: Computer 应能直接读取 SDCard")
    void sdCard_shouldBeReadableByComputer() {
        Computer computer = new Computer();
        SDCard sdCard = new SDCardImpl();
        String result = computer.readSD(sdCard);
        assertThat(result).isEqualTo("sd card read a msg: Hello Fan!");
    }

    @Test
    @DisplayName("SDCard: 传入 null 应抛出 NullPointerException")
    void sdCard_shouldThrowWhenNull() {
        Computer computer = new Computer();
        assertThatThrownBy(() -> computer.readSD(null))
                .isInstanceOf(NullPointerException.class);
    }

    // ===================== TFCard 直接读取 =====================

    @Test
    @DisplayName("TFCard: readTF 应返回正确内容")
    void tfCard_shouldReturnCorrectContent() {
        TFCard tfCard = new TFCardImpl();
        assertThat(tfCard.readTF()).isEqualTo("tf card read msg: Hello king");
    }

    // ===================== 类适配器 =====================

    @Test
    @DisplayName("类适配器: SDClassAdapterTF 应实现 SDCard 接口")
    void classAdapter_shouldImplementSDCard() {
        SDClassAdapterTF adapter = new SDClassAdapterTF();
        assertThat(adapter).isInstanceOf(SDCard.class);
    }

    @Test
    @DisplayName("类适配器: Computer 应能通过适配器读取 TFCard")
    void classAdapter_shouldEnableComputerReadTFCard() {
        Computer computer = new Computer();
        SDClassAdapterTF adapter = new SDClassAdapterTF();
        String result = computer.readSD(adapter);
        assertThat(result).isEqualTo("tf card read msg: Hello king");
    }

    // ===================== 对象适配器 =====================

    @Test
    @DisplayName("对象适配器: SDObjectAdapterTF 应实现 SDCard 接口")
    void objectAdapter_shouldImplementSDCard() {
        SDObjectAdapterTF adapter = new SDObjectAdapterTF(new TFCardImpl());
        assertThat(adapter).isInstanceOf(SDCard.class);
    }

    @Test
    @DisplayName("对象适配器: Computer 应能通过对象适配器读取 TFCard")
    void objectAdapter_shouldEnableComputerReadTFCard() {
        Computer computer = new Computer();
        TFCard tfCard = new TFCardImpl();
        SDObjectAdapterTF adapter = new SDObjectAdapterTF(tfCard);
        String result = computer.readSD(adapter);
        assertThat(result).isEqualTo("tf card read msg: Hello king");
    }

    @Test
    @DisplayName("类适配器 vs 对象适配器: 都返回相同的 TFCard 数据")
    void classAndObjectAdapter_shouldReturnSameData() {
        SDClassAdapterTF classAdapter = new SDClassAdapterTF();
        SDObjectAdapterTF objectAdapter = new SDObjectAdapterTF(new TFCardImpl());

        assertThat(classAdapter.readSD()).isEqualTo(objectAdapter.readSD());
    }
}

package com.fanjiabao.design.pattern.creator.builder;

import com.fanjiabao.design.pattern.creator.builder.extension.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 建造者模式单元测试
 */
class BuilderTest {

    // ===================== Director + Builder =====================

    @Test
    @DisplayName("建造者: Director + MobileBuilder 应构建摩拜单车")
    void mobileBuilder_shouldBuildMobileBike() {
        Director director = new Director(new MobileBuilder());
        Bike bike = director.construct();

        assertThat(bike).isNotNull();
        assertThat(bike.getFrame()).isEqualTo("碳纤维车架");
        assertThat(bike.getSeat()).isEqualTo("真皮车座");
    }

    @Test
    @DisplayName("建造者: Director + OfoBuilder 应构建 ofo 单车")
    void ofoBuilder_shouldBuildOfoBike() {
        Director director = new Director(new OfoBuilder());
        Bike bike = director.construct();

        assertThat(bike).isNotNull();
        assertThat(bike.getFrame()).isEqualTo("铝合金车架");
        assertThat(bike.getSeat()).isEqualTo("橡胶车座");
    }

    @Test
    @DisplayName("建造者: 不同 Builder 应产出不同产品")
    void differentBuilders_shouldProduceDifferentProducts() {
        Bike mobileBike = new Director(new MobileBuilder()).construct();
        Bike ofoBike = new Director(new OfoBuilder()).construct();

        assertThat(mobileBike).isNotSameAs(ofoBike);
        assertThat(mobileBike.getFrame()).isNotEqualTo(ofoBike.getFrame());
        assertThat(mobileBike.getSeat()).isNotEqualTo(ofoBike.getSeat());
    }

    @Test
    @DisplayName("建造者: Builder.construct() 应返回完整构建的 Bike")
    void builderConstruct_shouldReturnCompleteBike() {
        MobileBuilder builder = new MobileBuilder();
        Bike bike = builder.construct();

        assertThat(bike).isNotNull();
        assertThat(bike.getFrame()).isEqualTo("碳纤维车架");
        assertThat(bike.getSeat()).isEqualTo("真皮车座");
    }

    // ===================== 建造者扩展-Phone =====================

    @Test
    @DisplayName("建造者扩展: Phone.Builder 链式调用应正确构建对象")
    void phoneBuilder_shouldBuildCorrectly() {
        Phone phone = new Phone.Builder()
                .cpu("骁龙8")
                .screen("三星")
                .memory("16GB")
                .mainboard("华硕")
                .build();

        assertThat(phone).isNotNull();
        assertThat(phone.toString()).contains("骁龙8", "三星", "16GB", "华硕");
    }

    @Test
    @DisplayName("建造者扩展: 部分属性设置应正常工作")
    void phoneBuilder_shouldWorkWithPartialProperties() {
        Phone phone = new Phone.Builder()
                .cpu("天玑")
                .screen("京东方")
                .build();

        assertThat(phone).isNotNull();
        assertThat(phone.toString()).contains("天玑", "京东方");
    }

    @Test
    @DisplayName("建造者扩展: 两次构建应返回不同对象")
    void phoneBuilder_shouldReturnDifferentObjects() {
        Phone.Builder builder = new Phone.Builder()
                .cpu("A17");
        Phone phone1 = builder.build();
        Phone phone2 = builder.build();

        assertThat(phone1).isNotSameAs(phone2);
    }
}

package com.fanjiabao.design.pattern.behavioral.mediator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 中介者模式单元测试
 */
class MediatorTest {

    @Test
    @DisplayName("中介者: MediatorStructure 应继承 Mediator")
    void mediatorStructure_shouldExtendMediator() {
        MediatorStructure mediator = new MediatorStructure();
        assertThat(mediator).isInstanceOf(Mediator.class);
    }

    @Test
    @DisplayName("中介者: HouseOwner 应继承 Person")
    void houseOwner_shouldExtendPerson() {
        MediatorStructure mediator = new MediatorStructure();
        HouseOwner owner = new HouseOwner("开发商", mediator);
        assertThat(owner).isInstanceOf(Person.class);
    }

    @Test
    @DisplayName("中介者: Tenant 应继承 Person")
    void tenant_shouldExtendPerson() {
        MediatorStructure mediator = new MediatorStructure();
        Tenant tenant = new Tenant("租户", mediator);
        assertThat(tenant).isInstanceOf(Person.class);
    }

    @Test
    @DisplayName("中介者: 租户发消息应转发给房主")
    void tenantMessage_shouldBeForwardedToHouseOwner() {
        MediatorStructure mediator = new MediatorStructure();
        Tenant tenant = new Tenant("租户", mediator);
        HouseOwner owner = new HouseOwner("开发商", mediator);

        mediator.setTenant(tenant);
        mediator.setHouseOwner(owner);

        tenant.contact("我要租房");
    }

    @Test
    @DisplayName("中介者: 房主发消息应转发给租户")
    void houseOwnerMessage_shouldBeForwardedToTenant() {
        MediatorStructure mediator = new MediatorStructure();
        Tenant tenant = new Tenant("租户", mediator);
        HouseOwner owner = new HouseOwner("开发商", mediator);

        mediator.setTenant(tenant);
        mediator.setHouseOwner(owner);

        owner.contact("我有房源");
    }

    @Test
    @DisplayName("中介者: 完整通信流程")
    void mediator_shouldEnableFullCommunication() {
        MediatorStructure mediator = new MediatorStructure();
        Tenant tenant = new Tenant("租户", mediator);
        HouseOwner owner = new HouseOwner("开发商", mediator);

        mediator.setTenant(tenant);
        mediator.setHouseOwner(owner);

        tenant.contact("我要租房");
        owner.contact("我有，来");
    }
}

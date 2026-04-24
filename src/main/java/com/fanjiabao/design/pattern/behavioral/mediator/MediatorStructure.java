package com.fanjiabao.design.pattern.behavioral.mediator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 15:52
 * @description: 具体的中介者角色类
 */
public class MediatorStructure extends Mediator {

    // 聚合房主
    private HouseOwner houseOwner;

    // 聚合租户
    private Tenant tenant;

    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void contact(String message, Person person) {
        // 和不同的人沟通
        if(person == houseOwner) {
            tenant.getMessage(message);
        } else {
            houseOwner.getMessage(message);
        }
    }
}

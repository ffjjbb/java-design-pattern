package com.fanjiabao.design.pattern.behavioral.interpreter.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Order 单元测试
 * 测试订单对象的属性和行为
 */
public class OrderTest {

    @Test
    @DisplayName("测试创建 Order 对象")
    void testCreateOrder() {
        // 测试场景: 创建订单对象并验证属性
        Long id = 1L;
        String userLevel = "VIP";
        double amount = 1000.0;
        
        Order order = new Order(id, userLevel, amount);
        
        // 断言: 属性正确设置
        assertThat(order.getId()).isEqualTo(id);
        assertThat(order.getUserLevel()).isEqualTo(userLevel);
        assertThat(order.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("测试设置和获取折扣")
    void testSetAndGetDiscount() {
        // 测试场景: 设置折扣并验证
        Order order = new Order(1L, "VIP", 1000.0);
        double discount = 0.8;
        
        order.setDiscount(discount);
        
        // 断言: 折扣正确设置
        assertThat(order.getDiscount()).isEqualTo(discount);
    }

    @Test
    @DisplayName("测试设置和获取备注")
    void testSetAndGetRemark() {
        // 测试场景: 设置备注并验证
        Order order = new Order(1L, "VIP", 1000.0);
        String remark = "VIP用户享受8折优惠";
        
        order.setRemark(remark);
        
        // 断言: 备注正确设置
        assertThat(order.getRemark()).isEqualTo(remark);
    }

    @Test
    @DisplayName("测试不同用户等级的订单")
    void testDifferentUserLevels() {
        // 测试场景: 创建不同用户等级的订单
        Order vipOrder = new Order(1L, "VIP", 1000.0);
        Order normalOrder = new Order(2L, "NORMAL", 500.0);
        
        // 断言: 用户等级不同
        assertThat(vipOrder.getUserLevel()).isEqualTo("VIP");
        assertThat(normalOrder.getUserLevel()).isEqualTo("NORMAL");
    }

    @Test
    @DisplayName("测试不同金额的订单")
    void testDifferentAmounts() {
        // 测试场景: 创建不同金额的订单
        Order smallOrder = new Order(1L, "NORMAL", 100.0);
        Order largeOrder = new Order(2L, "NORMAL", 10000.0);
        
        // 断言: 金额不同
        assertThat(smallOrder.getAmount()).isEqualTo(100.0);
        assertThat(largeOrder.getAmount()).isEqualTo(10000.0);
        assertThat(largeOrder.getAmount()).isGreaterThan(smallOrder.getAmount());
    }

    @Test
    @DisplayName("测试修改订单属性")
    void testModifyOrderProperties() {
        // 测试场景: 修改订单的各个属性
        Order order = new Order(1L, "NORMAL", 500.0);
        
        order.setUserLevel("VIP");
        order.setAmount(1000.0);
        order.setDiscount(0.8);
        order.setRemark("升级为VIP");
        
        // 断言: 所有属性正确修改
        assertThat(order.getUserLevel()).isEqualTo("VIP");
        assertThat(order.getAmount()).isEqualTo(1000.0);
        assertThat(order.getDiscount()).isEqualTo(0.8);
        assertThat(order.getRemark()).isEqualTo("升级为VIP");
    }

    @Test
    @DisplayName("测试计算折扣后价格")
    void testCalculateDiscountedPrice() {
        // 测试场景: 计算折扣后的价格
        Order order = new Order(1L, "VIP", 1000.0);
        order.setDiscount(0.8);
        
        double finalPrice = order.getAmount() * order.getDiscount();
        
        // 断言: 折扣后价格正确
        assertThat(finalPrice).isEqualTo(800.0);
    }
}

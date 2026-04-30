package com.fanjiabao.design.pattern.behavioral.interpreter.source_code_analysis;

import lombok.Data;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/30 15:20
 * @description: 订单对象, 也就是 Drools 中的 Fact(事实对象)
 * <p>
 * 在解释器模式中, 它类似 Context 上下文中的数据。
 * 规则表达式会围绕这个对象进行解释：
 *      Order(amount >= 1000)
 *      Order(userLevel == "VIP")
 */
@Data
public class Order {

    private Long id;

    /**
     * 用户等级
     */
    private String userLevel;

    /**
     * 订单金额
     */
    private double amount;

    /**
     * 折扣
     */
    private double discount;

    /**
     * 规则命中说明
     */
    private String remark;

    public Order(Long id, String userLevel, double amount) {
        this.id = id;
        this.userLevel = userLevel;
        this.amount = amount;
    }

}

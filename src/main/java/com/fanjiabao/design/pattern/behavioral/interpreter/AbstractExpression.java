package com.fanjiabao.design.pattern.behavioral.interpreter;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/30 12:11
 * @description: 抽象表达式类
 */
public abstract class AbstractExpression {

    public abstract int interpret(Context context);
}

package com.fanjiabao.design.pattern.behavioral.state;

/**
 * 电梯接口
 */
public interface ILift {

    // 电梯状态的常量
    int OPENING_STATE = 1;

    int CLOSING_STATE = 2;

    int RUNNING_STATE = 3;

    int STOPPING_STATE = 4;

    // 设置电梯状态
    void setState(int state);

    void open();

    void close();

    void run();

    void stop();
}

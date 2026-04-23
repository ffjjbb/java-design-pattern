package com.fanjiabao.design.pattern.behavioral.state;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/23 15:56
 * @description: 电梯类(ILift的子实现类)
 */
public class Lift implements ILift {

    // 记录当前状态
    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public void open() {
        switch (state) {
            case OPENING_STATE :
                // 运行状态怎么能开门呢, 保持不变
                break;
            case CLOSING_STATE :
                System.out.println("电梯打开了...");
                setState(OPENING_STATE);
                break;
            case STOPPING_STATE :
                System.out.println("电梯打开了...");
                setState(OPENING_STATE);
                break;
            case RUNNING_STATE :
                // 运行状态保持不变
                break;
        }
    }

    public void close() {
        switch (this.state) {
            case OPENING_STATE:
                System.out.println("电梯关门了...");
                this.setState(CLOSING_STATE);
                break;
            case CLOSING_STATE:
                break;
            case RUNNING_STATE:
                break;
            case STOPPING_STATE:
                break;
        }
    }

    public void run() {
        switch (this.state) {
            case OPENING_STATE:
                // 电梯不能开着门走
                break;
            case CLOSING_STATE:
                System.out.println("电梯开始运行了...");
                this.setState(RUNNING_STATE);
                break;
            case RUNNING_STATE:
                break;
            case STOPPING_STATE:
                System.out.println("电梯开始运行了...");
                this.setState(RUNNING_STATE);
                break;
        }
    }

    public void stop() {
        switch (this.state) {
            case OPENING_STATE:
                break;
            case CLOSING_STATE:
                System.out.println("电梯停止了...");
                this.setState(STOPPING_STATE);
                break;
            case RUNNING_STATE:
                // 运行时也可停止了
                System.out.println("电梯停止了...");
                this.setState(STOPPING_STATE);
                break;
            case STOPPING_STATE:
                break;
        }
    }
}


package com.fanjiabao.design.pattern.behavioral.responsibility;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/22 8:58
 * @description: 抽象处理者类
 */
public abstract class Handler {

    protected final static int NUM_ONE = 1;

    protected final static int NUM_THREE = 3;

    protected final static int NUM_SEVEN = 7;

    // 请求天数区间
    private int numStart;

    private int numEnd;

    // 后续者
    private Handler nextHandler;

    public Handler(int numStart) {
        this.numStart = numStart;
    }

    public Handler(int numStart, int numEnd) {
        this.numStart = numStart;
        this.numEnd = numEnd;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // 处理请求条的方法
    protected abstract void handleLeave(LeaveRequest leave);

    // 提交请求条
    public final void submit(LeaveRequest leave) {
        // 当前领导审批
        this.handleLeave(leave);
        if (this.nextHandler != null && leave.getNum() > this.numEnd) {
            // 提交后续者
            this.nextHandler.submit(leave);
        } else {
            System.out.println("流程结束!");
        }
    }

}

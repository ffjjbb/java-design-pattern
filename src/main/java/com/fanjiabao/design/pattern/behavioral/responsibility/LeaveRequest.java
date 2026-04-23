package com.fanjiabao.design.pattern.behavioral.responsibility;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/22 9:00
 * @description: 请假条类
 */
public class LeaveRequest {

    private String name;

    private int num;

    private String content;

    public LeaveRequest(String name, int num, String content) {
        this.name = name;
        this.num = num;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public String getContent() {
        return content;
    }
}


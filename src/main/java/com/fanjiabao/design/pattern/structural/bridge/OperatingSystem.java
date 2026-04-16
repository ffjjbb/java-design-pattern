package com.fanjiabao.design.pattern.structural.bridge;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 10:03
 * @description: 抽象的操作系统类(抽象化角色)
 */
public abstract class OperatingSystem {

    protected VideoFile videoFile;

    public OperatingSystem(VideoFile videoFile) {
        this.videoFile = videoFile;
    }

    public abstract void play(String fileName);
}

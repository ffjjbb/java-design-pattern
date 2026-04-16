package com.fanjiabao.design.pattern.structural.bridge;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 10:04
 * @description: Mac操作系统(扩展抽象化角色)
 */
public class Mac extends OperatingSystem {

    public Mac(VideoFile videoFile) {
        super(videoFile);
    }

    public void play(String fileName) {
        videoFile.decode(fileName);
    }

}


package com.fanjiabao.design.pattern.structural.bridge;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 10:02
 * @description: 扩展抽象化角色(windows操作系统)
 */
public class Windows extends OperatingSystem {

    public Windows(VideoFile videoFile) {
        super(videoFile);
    }

    public void play(String fileName) {
        videoFile.decode(fileName);
    }
}

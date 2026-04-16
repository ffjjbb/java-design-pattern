package com.fanjiabao.design.pattern.structural.bridge;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 10:02
 * @description: rmvb视频文件(具体的实现化角色)
 */
public class RmvbFile implements VideoFile {

    public void decode(String fileName) {
        System.out.println("rmvb视频文件: " + fileName);
    }

}

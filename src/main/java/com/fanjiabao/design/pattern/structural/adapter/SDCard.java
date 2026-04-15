package com.fanjiabao.design.pattern.structural.adapter;

/**
 * SD卡接口
 */
public interface SDCard {

    String readSD();

    void writeSD(String msg);

}

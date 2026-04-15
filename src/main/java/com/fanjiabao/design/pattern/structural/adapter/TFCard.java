package com.fanjiabao.design.pattern.structural.adapter;

/**
 * TF卡接口
 */
public interface TFCard {

    String readTF();

    void writeTF(String msg);

}

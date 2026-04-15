package com.fanjiabao.design.pattern.structural.adapter.class_adapter;

import com.fanjiabao.design.pattern.structural.adapter.SDCard;
import com.fanjiabao.design.pattern.structural.adapter.TFCardImpl;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:06
 * @description: 适配器类(SD兼容TF)
 */
public class SDClassAdapterTF extends TFCardImpl implements SDCard {

    @Override
    public String readSD() {
        System.out.println("adapter read tf card ");
        return readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        writeTF(msg);
    }

}

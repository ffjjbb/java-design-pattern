package com.fanjiabao.design.pattern.structural.adapter.object_adapter;

import com.fanjiabao.design.pattern.structural.adapter.SDCard;
import com.fanjiabao.design.pattern.structural.adapter.TFCard;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:28
 * @description: 适配器对象(SD兼容TF)
 */
public class SDObjectAdapterTF implements SDCard {

    private TFCard tfCard;

    public SDObjectAdapterTF(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public String readSD() {
        System.out.println("adapter read tf card ");
        return tfCard.readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        tfCard.writeTF(msg);
    }

}

package com.fanjiabao.design.pattern.structural.decorator.extension;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 18:50
 * @description: 装饰者模式: BufferedWriter 解析
 */
public class DecoratorExtension {

    /**
     * 核心思想:
     *  BufferedWriter 在不改变原对象(FileWriter)的情况下, 通过组合的方式持有它, 并动态增强其功能(增加缓冲区，提高性能)
     * <p>
     * 角色划分:
     *  - 抽象组件(Component): Writer
     *  - 具体组件(ConcreteComponent): FileWriter
     *  - 装饰者(Decorator): BufferedWriter
     * <p>
     * 执行流程:
     *  1.new BufferedWriter(fw)
     *      --> 内部持有 FileWriter(this.out = fw)
     *  2.bw.write("hello Buffered")
     *      --> 先写入 BufferedWriter 内部缓冲区(char[])
     *  3.bw.close()
     *      --> 触发 flushBuffer()
     *      --> 调用 out.write(...)(即 FileWriter)
     *      --> 数据真正写入文件
     */
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("src/test/resources/DecoratorExtension.log");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("hello Buffered");
        bw.close();
    }

}

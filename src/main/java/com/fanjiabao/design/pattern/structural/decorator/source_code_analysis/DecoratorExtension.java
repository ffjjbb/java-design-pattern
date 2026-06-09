package com.fanjiabao.design.pattern.structural.decorator.source_code_analysis;

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
     * 调用流程:
     *  1.创建装饰者
     *      BufferedWriter bw = new BufferedWriter(fw);
     *          --> 内部持有 FileWriter(this.out = fw;) => BufferedWriter.java:98
     *  2.写入数据
     *      bw.write("hello Buffered");
     *          // 进入 Writer.write(String), String → char[](字符数组转换, 用于复用, 避免频繁创建小数组)
     *          --> cbuf = writeBuffer; => Writer.java:285
     *          --> str.getChars(off, (off + len), cbuf, 0); => Writer.java:289
     *          --> write(cbuf, 0, len); => Writer.java:290(通过多态调用 BufferedWriter.write(char[], off, len))
     *          --> public void write(char cbuf[], int off, int len) {...} => BufferedWriter.java:169
     *              // 写入缓冲区
     *              --> System.arraycopy(cbuf, b, cb, nextChar, d); => BufferedWriter.java:191
     *  3.关闭流
     *      bw.close();
     *          --> flushBuffer(); => BufferedWriter.java:268
     *              // 调用 FileWriter 写入文件
     *              --> out.write(cb, 0, nextChar); => BufferedWriter.java:120
     */
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("log/DecoratorExtension.log");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("hello Buffered");
        bw.close();
    }

}

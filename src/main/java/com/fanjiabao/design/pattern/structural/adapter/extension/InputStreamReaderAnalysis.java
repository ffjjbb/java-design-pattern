package com.fanjiabao.design.pattern.structural.adapter.extension;

import java.io.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 10:33
 * @description: 对象适配器 InputStreamReader 解析
 */
public class InputStreamReaderAnalysis {

    /**
     * Target(目标接口): Reader 客户端想要的 "字符读取能力"
     * Adaptee(被适配者): InputStream 只能提供 "字节读取能力"
     * Adapter(适配器): InputStreamReader 把 byte → char
     * <p>
     * 在 reader.read(buffer) 方法中, 内部通过 StreamDecoder 将字节流解码为字符流, 从而实现接口兼容。
     * 调用逻辑:
     *  StreamDecoder.java:155
     *      --> StreamDecoder.java:300  = implRead(cbuf, off, off + len)
     *          --> StreamDecoder.java:316 = decoder.decode(bb, cb, eof)
     * InputStreamReader 在 read() 方法中, 委托给 StreamDecoder 实现读取逻辑。在 implRead() 方法中,
     * 通过 CharsetDecoder.decode(ByteBuffer, CharBuffer, ...) 将 InputStream 读取的字节数据解码为字符数据, 从而完成从字
     * 节流到字符流的适配。
     */
    public static void main(String[] args) throws IOException {
        System.out.println("========== 直接使用 InputStream（字节流） ==========");
        // 数据源, 读取原始 byte 数据
        InputStream inputStream = new FileInputStream("src/main/resources/simpleConfigExtensionBean.properties");
        int data;
        while ((data = inputStream.read()) != -1) {
            // 输出字节值
            System.out.print(data + " ");
        }
        inputStream.close();


        // InputStreamReader 是一个典型的对象适配器, 它通过组合 InputStream, 将原本只能读取字节的接口, 转换为可以读取字符的 Reader 接口。
        System.out.println("\n\n========== 使用 InputStreamReader（适配器） ==========");
        InputStream inputStream2 = new FileInputStream("src/main/resources/simpleConfigExtensionBean.properties");
        Reader reader = new InputStreamReader(inputStream2, "UTF-8");
        char[] buffer = new char[10000];
        int len = reader.read(buffer);
        System.out.println("内容: " + new String(buffer, 0, len));
        reader.close();
    }

}

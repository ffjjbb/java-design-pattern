package com.fanjiabao.design.pattern.creator.builder.source_code_analysis;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 18:11
 * @description: 分析 StringBuilder 的建造者模式:
 *  - StringBuilder 是 JDK 内置的典型 Builder 模式示例
 *  - 客户端按步骤调用 append() 构建最终 Product(String)
 * <p>
 * 模式角色:
 *  - Builder: StringBuilder
 *  - Product: String
 *  - Client: 调用 append() 方法的代码
 */
public class StringBuilderBuilderAnalysis {

    /**
     * 1.创建 Builder 对象
     *      --> StringBuilder sb = new StringBuilder();
     *          方法作用: 初始化空字符序列, 准备构建最终对象
     *          内部:
     *              - char[] value = new char[16]; // 默认容量16
     *              - count = 0; // 当前长度
     * 2.逐步构建 Product
     *      --> sb.append("Hello")
     *          方法作用: 在内部 char[] 中添加字符串内容
     *          内部调用流程:
     *              1) ensureCapacityInternal(count + str.length())
     *                  检查内部数组容量是否足够
     *              2) System.arraycopy(...) // 拷贝字符
     *              3) 更新 count
     * 3.构建最终 Product
     *      --> String result = sb.toString();
     *          方法作用: 返回最终的不可变 String 对象
     *          内部调用流程:
     *              1) new String(sb.value, 0, sb.count)
     *                  - 将内部 char[] 拷贝为 String 对象
     *              2) 返回 String, Product 完成
     * 4. 输出结果
     *      --> System.out.println(result);
     *          输出: Hello World
     * <p>
     * 模式体现
     *      - Builder: StringBuilder
     *      - Product: String
     *      - Client: main 方法
     *      - 透明构建: append 方法可链式调用, 多次调用逐步构建 Product
     *      - 灵活性: 可按需添加字符串, 最终通过 toString() 获取完整对象
     */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        String result = sb.toString();
        System.out.println(result);
    }

}

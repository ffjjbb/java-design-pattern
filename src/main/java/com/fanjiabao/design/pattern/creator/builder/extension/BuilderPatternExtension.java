package com.fanjiabao.design.pattern.creator.builder.extension;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/13 17:32
 * @description: 模式扩展:
 *  在开发中还有一个常用的使用方式, 就是当一个类构造器需要传入很多参数时, 如果创建这个类的实例, 代码可读性会非常差, 而且很容易引入错误,
 *  此时就可以利用建造者模式进行重构。
 */
public class BuilderPatternExtension {

    public static void main(String[] args) {
        // 传统方式创建对象, 可读性差, 容易出错
        OriginalPhone originalPhone = new OriginalPhone("intel","三星屏幕","金士顿","华硕");
        System.out.println(originalPhone);

        // 使用建造者模式创建对象, 代码可读性高, 不容易出错
        Phone phone = new Phone.Builder()
                .cpu("intel")
                .mainboard("华硕")
                .memory("金士顿")
                .screen("三星")
                .build();
        System.out.println(phone);
    }

}

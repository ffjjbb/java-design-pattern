package com.fanjiabao.design.pattern.structural.flyweight.source_code_analysis;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 10:55
 * @description: Integer类使用了享元模式
 */
public class IntegerFlyweightAnalysis {

    /**
     * 调用流程:
     *  1.Integer.valueOf((int) 127);
     *      --> return IntegerCache.cache[i + (-IntegerCache.low)]; = Integer.java:1080
     *  2.private static class IntegerCache {...} (缓存 [-128 ~ high] 的 Integer 对象, 避免重复创建)
     *      CDS: 是 JVM 的类数据共享机制, JVM 启动时直接复用已缓存的 Integer 数组(跨进程共享)
     *      --> CDS.initializeFromArchive(IntegerCache.class); = Integer.java:1042
     *          --> c[i] = new Integer(j++); = java.base/java/lang/Integer.java:1050
     *              --> archivedCache = c;
     *                  --> cache = archivedCache;
     */
    public static void main(String[] args) {
        Integer i2 = 127; // 编译后等价于 ↓
        Integer i1 = Integer.valueOf((int) 127);
        System.out.println("i1 和 i2对象是否是同一个对象: " + (i1 == i2));

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println("i3 和 i4 对象是否是同一个对象: " + (i3 == i4));
    }

    /**
     * CDS(Class Data Sharing)是 JVM 提供的一种类数据共享机制, 它会将已经加载和解析好的类元数据保存到归档文件中, 在后续 JVM 启动
     * 时直接复用, 从而减少类加载和解析的开销, 提高启动速度, 并支持多个 JVM 进程共享类数据。
     * 在 IntegerCache 中， 通过 CDS 可以直接加载缓存数组， 避免重复创建 Integer 对象。
     * <p>
     * JVM优化三层:
     *  1.编译期优化(JIT)
     *  2.运行期优化(GC/分代)
     *  3.启动期优化(CDS)
     */
    public void cds() {}

}

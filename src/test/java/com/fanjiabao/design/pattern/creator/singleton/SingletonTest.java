package com.fanjiabao.design.pattern.creator.singleton;

import com.fanjiabao.design.pattern.creator.singleton.hungry.one.HungryPlanOne;
import com.fanjiabao.design.pattern.creator.singleton.hungry.two.HungryPlanTwo;
import com.fanjiabao.design.pattern.creator.singleton.hungry.three.HungryPlanThree;
import com.fanjiabao.design.pattern.creator.singleton.lazy.one.LazyPlanOne;
import com.fanjiabao.design.pattern.creator.singleton.lazy.two.LazyPlanTwo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 单例模式单元测试
 */
class SingletonTest {

    // ===================== 饿汉式-静态变量 =====================

    @Test
    @DisplayName("饿汉式静态变量: getInstance 应始终返回同一实例")
    void hungryPlanOne_shouldReturnSameInstance() {
        HungryPlanOne instance1 = HungryPlanOne.getInstance();
        HungryPlanOne instance2 = HungryPlanOne.getInstance();
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("饿汉式静态变量: 多线程下应返回同一实例")
    void hungryPlanOne_shouldReturnSameInstanceUnderConcurrency() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<HungryPlanOne> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                instances.add(HungryPlanOne.getInstance());
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        assertThat(instances).hasSize(1);
    }

    // ===================== 饿汉式-静态代码块 =====================

    @Test
    @DisplayName("饿汉式静态代码块: getInstance 应始终返回同一实例")
    void hungryPlanTwo_shouldReturnSameInstance() {
        HungryPlanTwo instance1 = HungryPlanTwo.getInstance();
        HungryPlanTwo instance2 = HungryPlanTwo.getInstance();
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("饿汉式静态代码块: 多线程下应返回同一实例")
    void hungryPlanTwo_shouldReturnSameInstanceUnderConcurrency() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<HungryPlanTwo> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                instances.add(HungryPlanTwo.getInstance());
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        assertThat(instances).hasSize(1);
    }

    // ===================== 饿汉式-枚举 =====================

    @Test
    @DisplayName("饿汉式枚举: INSTANCE 应始终是同一个对象")
    void hungryPlanThree_shouldReturnSameInstance() {
        HungryPlanThree instance1 = HungryPlanThree.INSTANCE;
        HungryPlanThree instance2 = HungryPlanThree.INSTANCE;
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("饿汉式枚举: 反射攻击应抛出异常")
    void hungryPlanThree_shouldRejectReflectionAttack() {
        assertThatThrownBy(() -> {
            Constructor<HungryPlanThree> constructor = HungryPlanThree.class.getDeclaredConstructor(String.class, int.class);
            constructor.setAccessible(true);
            constructor.newInstance("INSTANCE", 0);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Cannot reflectively create enum objects");
    }

    // ===================== 懒汉式-双重检查锁 =====================

    @Test
    @DisplayName("懒汉式双重检查锁: getInstance 应始终返回同一实例")
    void lazyPlanOne_shouldReturnSameInstance() {
        LazyPlanOne instance1 = LazyPlanOne.getInstance();
        LazyPlanOne instance2 = LazyPlanOne.getInstance();
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("懒汉式双重检查锁: 多线程下应返回同一实例")
    void lazyPlanOne_shouldReturnSameInstanceUnderConcurrency() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<LazyPlanOne> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                instances.add(LazyPlanOne.getInstance());
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        assertThat(instances).hasSize(1);
    }

    // ===================== 懒汉式-静态内部类 =====================

    @Test
    @DisplayName("懒汉式静态内部类: getInstance 应始终返回同一实例")
    void lazyPlanTwo_shouldReturnSameInstance() {
        LazyPlanTwo instance1 = LazyPlanTwo.getInstance();
        LazyPlanTwo instance2 = LazyPlanTwo.getInstance();
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("懒汉式静态内部类: 多线程下应返回同一实例")
    void lazyPlanTwo_shouldReturnSameInstanceUnderConcurrency() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<LazyPlanTwo> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                instances.add(LazyPlanTwo.getInstance());
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        assertThat(instances).hasSize(1);
    }

    @Test
    @DisplayName("懒汉式静态内部类: 序列化反序列化应不破坏单例(readResolve)")
    void lazyPlanTwo_shouldPreserveSingletonAfterSerialization() throws Exception {
        LazyPlanTwo instance1 = LazyPlanTwo.getInstance();

        // 序列化到内存
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(instance1);
        oos.close();

        // 从内存反序列化
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        LazyPlanTwo instance2 = (LazyPlanTwo) ois.readObject();
        ois.close();

        assertThat(instance1).isSameAs(instance2);
    }
}

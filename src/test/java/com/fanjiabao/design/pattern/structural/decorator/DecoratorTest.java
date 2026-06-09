package com.fanjiabao.design.pattern.structural.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 装饰器模式单元测试
 */
class DecoratorTest {

    @Test
    @DisplayName("装饰器: 炒饭基础价格应为 10")
    void friedRice_shouldHaveBasePrice() {
        FastFood food = new FriedRice();
        assertThat(food.cost()).isEqualTo(10.0f);
        assertThat(food.getDesc()).isEqualTo("炒饭");
    }

    @Test
    @DisplayName("装饰器: 炒面基础价格应为 12")
    void friedNoodles_shouldHaveBasePrice() {
        FastFood food = new FriedNoodles();
        assertThat(food.cost()).isEqualTo(12.0f);
        assertThat(food.getDesc()).isEqualTo("炒面");
    }

    @Test
    @DisplayName("装饰器: 炒饭加蛋后价格应为 11")
    void friedRiceAndEgg_shouldCost11() {
        FastFood food = new Egg(new FriedRice());
        assertThat(food.cost()).isEqualTo(11.0f);
    }

    @Test
    @DisplayName("装饰器: 炒饭加蛋加培根价格应为 13")
    void friedRiceWithEggAndBacon_shouldCost13() {
        FastFood food = new Bacon(new Egg(new FriedRice()));
        assertThat(food.cost()).isEqualTo(13.0f);
    }

    @Test
    @DisplayName("装饰器: 多层装饰应累加描述")
    void multiLayerDecoration_shouldAccumulateDescription() {
        FastFood food = new Bacon(new Egg(new FriedRice()));
        String desc = food.getDesc();
        assertThat(desc).contains("培根", "鸡蛋", "炒饭");
    }

    @Test
    @DisplayName("装饰器: 炒面加两个蛋加培根价格应为 16")
    void friedNoodlesWithTwoEggsAndBacon_shouldCost16() {
        FastFood food = new Bacon(new Egg(new Egg(new FriedNoodles())));
        assertThat(food.cost()).isEqualTo(16.0f);
    }

    @Test
    @DisplayName("装饰器: Garnish 应持有被装饰对象的引用")
    void garnish_shouldHoldDecoratedObject() {
        FastFood rice = new FriedRice();
        Egg egg = new Egg(rice);
        assertThat(egg.getFastFood()).isSameAs(rice);
    }

    @Test
    @DisplayName("装饰器: 装饰后可继续包装")
    void decorator_shouldAllowChaining() {
        FastFood food = new FriedRice();
        food = new Egg(food);
        food = new Bacon(food);
        assertThat(food.cost()).isGreaterThan(10.0f);
    }
}

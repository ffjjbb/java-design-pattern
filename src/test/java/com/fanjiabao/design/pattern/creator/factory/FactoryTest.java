package com.fanjiabao.design.pattern.creator.factory;

import com.fanjiabao.design.pattern.creator.factory.abstract_factory.AmericanDessertFactory;
import com.fanjiabao.design.pattern.creator.factory.abstract_factory.DessertFactory;
import com.fanjiabao.design.pattern.creator.factory.abstract_factory.ItalyDessertFactory;
import com.fanjiabao.design.pattern.creator.factory.method.*;
import com.fanjiabao.design.pattern.creator.factory.simple.SimpleFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 工厂模式单元测试
 */
class FactoryTest {

    // ===================== 简单工厂 =====================

    @Test
    @DisplayName("简单工厂: americano 参数应返回 AmericanCoffee")
    void simpleFactory_shouldReturnAmericanCoffee() {
        SimpleFactory factory = new SimpleFactory();
        Coffee coffee = factory.createCoffee("americano");
        assertThat(coffee).isInstanceOf(AmericanCoffee.class);
        assertThat(coffee.getName()).isEqualTo("美式咖啡");
    }

    @Test
    @DisplayName("简单工厂: latte 参数应返回 LatteCoffee")
    void simpleFactory_shouldReturnLatteCoffee() {
        SimpleFactory factory = new SimpleFactory();
        Coffee coffee = factory.createCoffee("latte");
        assertThat(coffee).isInstanceOf(LatteCoffee.class);
        assertThat(coffee.getName()).isEqualTo("拿铁咖啡");
    }

    @Test
    @DisplayName("简单工厂: 未知类型应返回 null")
    void simpleFactory_shouldReturnNullForUnknownType() {
        SimpleFactory factory = new SimpleFactory();
        Coffee coffee = factory.createCoffee("unknown");
        assertThat(coffee).isNull();
    }

    // ===================== 工厂方法 =====================

    @Test
    @DisplayName("工厂方法: AmericanCoffeeFactory 应创建 AmericanCoffee")
    void methodFactory_shouldCreateAmericanCoffee() {
        CoffeeStore store = new CoffeeStore();
        store.setFactory(new AmericanCoffeeFactory());
        Coffee coffee = store.orderCoffee();
        assertThat(coffee).isInstanceOf(AmericanCoffee.class);
        assertThat(coffee.getName()).isEqualTo("美式咖啡");
    }

    @Test
    @DisplayName("工厂方法: LatteCoffeeFactory 应创建 LatteCoffee")
    void methodFactory_shouldCreateLatteCoffee() {
        CoffeeStore store = new CoffeeStore();
        store.setFactory(new LatteCoffeeFactory());
        Coffee coffee = store.orderCoffee();
        assertThat(coffee).isInstanceOf(LatteCoffee.class);
        assertThat(coffee.getName()).isEqualTo("拿铁咖啡");
    }

    @Test
    @DisplayName("工厂方法: 切换工厂应返回不同产品")
    void methodFactory_shouldSwitchFactoryAtRuntime() {
        CoffeeStore store = new CoffeeStore();
        store.setFactory(new AmericanCoffeeFactory());
        Coffee coffee1 = store.orderCoffee();

        store.setFactory(new LatteCoffeeFactory());
        Coffee coffee2 = store.orderCoffee();

        assertThat(coffee1).isNotEqualTo(coffee2);
        assertThat(coffee1.getName()).isNotEqualTo(coffee2.getName());
    }

    // ===================== 抽象工厂 =====================

    @Test
    @DisplayName("抽象工厂: AmericanDessertFactory 应创建 AmericanCoffee + MatchaMousse")
    void abstractFactory_shouldCreateAmericanProductFamily() {
        DessertFactory factory = new AmericanDessertFactory();
        Coffee coffee = factory.createCoffee();
        Dessert dessert = factory.createDessert();

        assertThat(coffee).isInstanceOf(AmericanCoffee.class);
        assertThat(coffee.getName()).isEqualTo("美式咖啡");
        assertThat(dessert).isInstanceOf(MatchaMousse.class);
    }

    @Test
    @DisplayName("抽象工厂: ItalyDessertFactory 应创建 LatteCoffee + Tiramisu")
    void abstractFactory_shouldCreateItalyProductFamily() {
        DessertFactory factory = new ItalyDessertFactory();
        Coffee coffee = factory.createCoffee();
        Dessert dessert = factory.createDessert();

        assertThat(coffee).isInstanceOf(LatteCoffee.class);
        assertThat(coffee.getName()).isEqualTo("拿铁咖啡");
        assertThat(dessert).isInstanceOf(Tiramisu.class);
    }

    @Test
    @DisplayName("抽象工厂: 不同工厂产品族互不相同")
    void abstractFactory_shouldProduceDifferentProductFamilies() {
        DessertFactory americanFactory = new AmericanDessertFactory();
        DessertFactory italyFactory = new ItalyDessertFactory();

        assertThat(americanFactory.createCoffee().getName())
                .isNotEqualTo(italyFactory.createCoffee().getName());
    }
}

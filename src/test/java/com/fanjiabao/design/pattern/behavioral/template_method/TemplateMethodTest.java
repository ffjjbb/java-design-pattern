package com.fanjiabao.design.pattern.behavioral.template_method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 模板方法模式单元测试
 */
class TemplateMethodTest {

    @Test
    @DisplayName("模板方法: AbstractClass cookProcess 应正常执行")
    void abstractClass_cookProcessShouldWork() {
        AbstractClass baoCai = new ConcreteClass_BaoCai();
        baoCai.cookProcess(); // 不抛异常即为通过
    }

    @Test
    @DisplayName("模板方法: ConcreteClass_BaoCai 应继承 AbstractClass")
    void baoCai_shouldExtendAbstractClass() {
        ConcreteClass_BaoCai baoCai = new ConcreteClass_BaoCai();
        assertThat(baoCai).isInstanceOf(AbstractClass.class);
    }

    @Test
    @DisplayName("模板方法: ConcreteClass_CaiXin 应继承 AbstractClass")
    void caiXin_shouldExtendAbstractClass() {
        ConcreteClass_CaiXin caiXin = new ConcreteClass_CaiXin();
        assertThat(caiXin).isInstanceOf(AbstractClass.class);
    }

    @Test
    @DisplayName("模板方法: 两种菜 cookProcess 应正常执行")
    void bothDishes_cookProcessShouldWork() {
        ConcreteClass_BaoCai baoCai = new ConcreteClass_BaoCai();
        baoCai.cookProcess();
        System.out.println("----");

        ConcreteClass_CaiXin caiXin = new ConcreteClass_CaiXin();
        caiXin.cookProcess();
    }

    @Test
    @DisplayName("模板方法: cookProcess 应包含固定步骤")
    void cookProcess_shouldIncludeFixedSteps() {
        AbstractClass dish = new ConcreteClass_BaoCai();
        dish.pourOil();
        dish.heatOil();
        dish.fry();
        // 固定步骤不抛异常
    }

    @Test
    @DisplayName("模板方法: pourVegetable 为抽象方法由子类实现")
    void pourVegetable_shouldBeAbstract() {
        ConcreteClass_BaoCai baoCai = new ConcreteClass_BaoCai();
        baoCai.pourVegetable(); // 炒包菜 -> 整点包菜

        ConcreteClass_CaiXin caiXin = new ConcreteClass_CaiXin();
        caiXin.pourVegetable(); // 炒菜心 -> 搞点菜心
    }
}

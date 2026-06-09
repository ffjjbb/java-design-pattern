package com.fanjiabao.design.pattern.structural.flyweight;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 享元模式单元测试
 */
class FlyweightTest {

    @Test
    @DisplayName("享元: BoxFactory 应为单例")
    void boxFactory_shouldBeSingleton() {
        BoxFactory factory1 = BoxFactory.getInstance();
        BoxFactory factory2 = BoxFactory.getInstance();
        assertThat(factory1).isSameAs(factory2);
    }

    @Test
    @DisplayName("享元: getShape(I) 应返回 IBox")
    void boxFactory_shouldReturnIBox() {
        AbstractBox box = BoxFactory.getInstance().getShape("I");
        assertThat(box).isInstanceOf(IBox.class);
        assertThat(box.getShape()).isEqualTo("I");
    }

    @Test
    @DisplayName("享元: getShape(L) 应返回 LBox")
    void boxFactory_shouldReturnLBox() {
        AbstractBox box = BoxFactory.getInstance().getShape("L");
        assertThat(box).isInstanceOf(LBox.class);
        assertThat(box.getShape()).isEqualTo("L");
    }

    @Test
    @DisplayName("享元: getShape(O) 应返回 OBox")
    void boxFactory_shouldReturnOBox() {
        AbstractBox box = BoxFactory.getInstance().getShape("O");
        assertThat(box).isInstanceOf(OBox.class);
        assertThat(box.getShape()).isEqualTo("O");
    }

    @Test
    @DisplayName("享元: 相同 shape 名称应返回同一实例")
    void flyweight_shouldReturnSameInstanceForSameShape() {
        BoxFactory factory = BoxFactory.getInstance();
        AbstractBox box1 = factory.getShape("O");
        AbstractBox box2 = factory.getShape("O");
        assertThat(box1).isSameAs(box2);
    }

    @Test
    @DisplayName("享元: 不同 shape 名称应返回不同实例")
    void flyweight_shouldReturnDifferentInstancesForDifferentShapes() {
        BoxFactory factory = BoxFactory.getInstance();
        AbstractBox box1 = factory.getShape("I");
        AbstractBox box2 = factory.getShape("L");
        assertThat(box1).isNotSameAs(box2);
    }

    @Test
    @DisplayName("享元: display 方法应正常执行")
    void flyweight_displayShouldWork() {
        AbstractBox box = BoxFactory.getInstance().getShape("I");
        box.display("红色");
    }
}

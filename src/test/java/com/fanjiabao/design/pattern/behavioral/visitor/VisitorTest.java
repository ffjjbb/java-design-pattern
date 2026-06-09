package com.fanjiabao.design.pattern.behavioral.visitor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 访问者模式单元测试
 */
class VisitorTest {

    @Test
    @DisplayName("访问者: Home 应管理 Animal 列表")
    void home_shouldManageAnimals() {
        Home home = new Home();
        home.add(new Cat());
        home.add(new Dog());
        // 不抛异常即为通过
    }

    @Test
    @DisplayName("访问者: Owner 喂食应正常执行")
    void owner_shouldFeed() {
        Home home = new Home();
        home.add(new Cat());
        home.add(new Dog());

        Owner owner = new Owner();
        home.action(owner);
    }

    @Test
    @DisplayName("访问者: Someone 喂食应正常执行")
    void someone_shouldFeed() {
        Home home = new Home();
        home.add(new Dog());

        Someone someone = new Someone();
        home.action(someone);
    }

    @Test
    @DisplayName("访问者: 不同访问者行为不同")
    void differentVisitors_shouldHaveDifferentBehavior() {
        Home home = new Home();
        home.add(new Cat());

        Owner owner = new Owner();
        Someone someone = new Someone();

        assertThat(owner).isInstanceOf(Person.class);
        assertThat(someone).isInstanceOf(Person.class);

        home.action(owner);
        home.action(someone);
    }

    @Test
    @DisplayName("访问者: Cat accept 应调用 Person.feed(Cat)")
    void cat_shouldAcceptPerson() {
        Cat cat = new Cat();
        Owner owner = new Owner();
        cat.accept(owner); // 猫说: 味正
    }

    @Test
    @DisplayName("访问者: Dog accept 应调用 Person.feed(Dog)")
    void dog_shouldAcceptPerson() {
        Dog dog = new Dog();
        Owner owner = new Owner();
        dog.accept(owner); // 狗说: 猫吃得不错
    }
}

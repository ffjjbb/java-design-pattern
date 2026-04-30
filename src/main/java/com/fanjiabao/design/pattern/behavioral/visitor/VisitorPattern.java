package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 10:55
 * @description: 行为型模式.访问者模式:
 *  封装一些作用于某种数据结构中的各元素的操作, 它可以在不改变这个数据结构的前提下定义作用于这些元素的新的操作。
 * <p>
 * 访问者模式包含以下主要角色:
 *  - 抽象访问者(Visitor)角色:
 *      定义了对每一个元素 'Element' 访问的行为, 它的参数就是可以访问的元素, 它的方法个数理论上来讲与元素类个数'Element' 的实现类个数是
 *      一样的, 从这点不难看出, 访问者模式要求元素类的个数不能改变。
 *  - 具体访问者(ConcreteVisitor)角色:
 *      给出对每一个元素类访问时所产生的具体行为。
 *  - 抽象元素(Element)角色:
 *      定义了一个接受访问者的方法 'accept', 其意义是指, 每一个元素都要可以被访问者访问。
 *  - 具体元素(ConcreteElement)角色:
 *      提供接受访问方法的具体实现, 而这个具体的实现, 通常情况下是使用访问者提供的访问该元素类的方法。
 *  - 对象结构(Object Structure)角色:
 *      定义当中所提到的对象结构, 对象结构是一个抽象表述, 具体点可以理解为一个具有容器性质或者复合对象特性的类, 它会含有一组元素 'Element',
 *      并且可以迭代这些元素, 供访问者访问。
 */
public class VisitorPattern {

    public static void main(String[] args) {
        Home home = new Home();
        home.add(new Cat());
        home.add(new Dog());

        Owner owner = new Owner();
        home.action(owner);
    }

}

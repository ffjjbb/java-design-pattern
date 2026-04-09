package com.fanjiabao.design.pattern.creator.prototype.shallow;

import com.fanjiabao.design.pattern.creator.prototype.Student;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 15:18
 * @description: 浅克隆
 * 工厂模式.原型模式:
 *  用一个已经创建的实例作为原型, 通过复制该原型对象来创建一个和原型对象相同的新对象。
 * 浅克隆:
 *  创建一个新对象, 新对象的属性和原来对象完全相同, 对于非基本类型属性, 仍指向原有属性所指向的对象的内存地址。
 */
public class ShallowCloning {

    /**
     * 使用场景:
     *  1.对象的创建非常复杂, 可以使用原型模式快捷的创建对象
     *  2.性能和安全要求比较高
     * ---------------------------
     * 原型模式包含如下角色：
     *  抽象原型类: 规定了具体原型对象必须实现的的 clone() 方法
     *  具体原型类: 实现抽象原型类的 clone() 方法, 它是可被复制的对象
     *  访问类: 使用具体原型类中的 clone() 方法来复制新的对象
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        // 创建原型对象
        ShallowPrototypeCitation citation = new ShallowPrototypeCitation();

        Student stu = new Student();
        stu.setName("石昊");
        citation.setStu(stu);

        ShallowPrototypeCitation citationClone = citation.clone();
        Student stu1 = citationClone.getStu();
        stu1.setName("克莱恩");

        System.out.println(citation ==  citationClone); // false
        System.out.println(stu ==  stu1);   // true
        citation.show();
        citationClone.show();
        // 引用类型拷贝了相同的内存地址, 都是克莱恩的奖
    }

}


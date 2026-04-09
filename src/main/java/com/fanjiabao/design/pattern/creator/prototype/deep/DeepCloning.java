package com.fanjiabao.design.pattern.creator.prototype.deep;

import com.fanjiabao.design.pattern.creator.prototype.Student;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 15:18
 * @description: 深克隆
 * 工厂模式.原型模式:
 *  用一个已经创建的实例作为原型, 通过复制该原型对象来创建一个和原型对象相同的新对象。
 * 深克隆:
 *  创建一个新对象, 属性中引用的其他对象也会被克隆, 不再指向原有对象地址
 */
public class DeepCloning {

    /**
     * 进行深克隆需要使用对象流
     */
    public static void main(String[] args) throws Exception {
        DeepPrototypeCitation citation = new DeepPrototypeCitation();
        Student stu = new Student();
        stu.setName("樊一");
        citation.setStu(stu);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("DeepPrototypeCitation.obj"));
        oos.writeObject(citation);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("DeepPrototypeCitation.obj"));
        DeepPrototypeCitation citationClone = (DeepPrototypeCitation) ois.readObject();
        ois.close();

        Student stu1 = citationClone.getStu();
        stu1.setName("樊二");

        System.out.println(citation ==  citationClone); // false
        System.out.println(stu ==  stu1);   // false
        citation.show();
        citationClone.show();
    }

}


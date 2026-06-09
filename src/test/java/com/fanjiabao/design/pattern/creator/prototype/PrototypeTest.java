package com.fanjiabao.design.pattern.creator.prototype;

import com.fanjiabao.design.pattern.creator.prototype.deep.DeepPrototypeCitation;
import com.fanjiabao.design.pattern.creator.prototype.shallow.ShallowPrototypeCitation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 原型模式单元测试
 */
class PrototypeTest {

    // ===================== 浅克隆 =====================

    @Test
    @DisplayName("浅克隆: clone 应为不同对象")
    void shallowClone_shouldCreateDifferentObject() throws CloneNotSupportedException {
        ShallowPrototypeCitation citation = new ShallowPrototypeCitation();
        Student stu = new Student();
        stu.setName("石昊");
        citation.setStu(stu);
        ShallowPrototypeCitation clone = citation.clone();

        assertThat(citation).isNotSameAs(clone);
    }

    @Test
    @DisplayName("浅克隆: 引用类型属性应指向同一对象")
    void shallowClone_shouldShareReferenceType() throws CloneNotSupportedException {
        ShallowPrototypeCitation citation = new ShallowPrototypeCitation();
        Student stu = new Student();
        stu.setName("石昊");
        citation.setStu(stu);
        ShallowPrototypeCitation clone = citation.clone();

        assertThat(citation.getStu()).isSameAs(clone.getStu());
    }

    @Test
    @DisplayName("浅克隆: 修改克隆对象的引用属性应影响原对象")
    void shallowClone_shouldAffectOriginalWhenModifyingSharedReference() throws CloneNotSupportedException {
        ShallowPrototypeCitation citation = new ShallowPrototypeCitation();
        Student stu = new Student();
        stu.setName("石昊");
        citation.setStu(stu);
        ShallowPrototypeCitation clone = citation.clone();

        clone.getStu().setName("荒天帝");
        assertThat(citation.getStu().getName()).isEqualTo("荒天帝");
    }

    // ===================== 深克隆 =====================

    @Test
    @DisplayName("深克隆: 序列化反序列化应为不同对象")
    void deepClone_shouldCreateDifferentObject() throws Exception {
        DeepPrototypeCitation citation = new DeepPrototypeCitation();
        Student stu = new Student();
        stu.setName("樊一");
        citation.setStu(stu);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(citation);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        DeepPrototypeCitation clone = (DeepPrototypeCitation) ois.readObject();
        ois.close();

        assertThat(citation).isNotSameAs(clone);
    }

    @Test
    @DisplayName("深克隆: 引用类型属性应为不同对象")
    void deepClone_shouldNotShareReferenceType() throws Exception {
        DeepPrototypeCitation citation = new DeepPrototypeCitation();
        Student stu = new Student();
        stu.setName("樊一");
        citation.setStu(stu);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(citation);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        DeepPrototypeCitation clone = (DeepPrototypeCitation) ois.readObject();
        ois.close();

        assertThat(citation.getStu()).isNotSameAs(clone.getStu());
    }

    @Test
    @DisplayName("深克隆: 修改克隆对象的引用属性不影响原对象")
    void deepClone_shouldNotAffectOriginalWhenModifyingReference() throws Exception {
        DeepPrototypeCitation citation = new DeepPrototypeCitation();
        Student stu = new Student();
        stu.setName("樊一");
        citation.setStu(stu);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(citation);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        DeepPrototypeCitation clone = (DeepPrototypeCitation) ois.readObject();
        ois.close();

        clone.getStu().setName("樊二");
        assertThat(citation.getStu().getName()).isEqualTo("樊一");
        assertThat(clone.getStu().getName()).isEqualTo("樊二");
    }
}

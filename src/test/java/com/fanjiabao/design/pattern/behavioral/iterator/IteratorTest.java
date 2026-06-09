package com.fanjiabao.design.pattern.behavioral.iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 迭代器模式单元测试
 */
class IteratorTest {

    @Test
    @DisplayName("迭代器: StudentAggregateImpl 应实现 StudentAggregate")
    void studentAggregateImpl_shouldImplementInterface() {
        StudentAggregateImpl aggregate = new StudentAggregateImpl();
        assertThat(aggregate).isInstanceOf(StudentAggregate.class);
    }

    @Test
    @DisplayName("迭代器: 添加学生应正确存储")
    void addStudent_shouldStoreCorrectly() {
        StudentAggregateImpl aggregate = new StudentAggregateImpl();
        Student stu = new Student("张三", "001");
        aggregate.addStudent(stu);

        StudentIterator iterator = aggregate.getStudentIterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next().getName()).isEqualTo("张三");
    }

    @Test
    @DisplayName("迭代器: removeStudent 应移除学生")
    void removeStudent_shouldRemoveCorrectly() {
        StudentAggregateImpl aggregate = new StudentAggregateImpl();
        Student stu = new Student("张三", "001");
        aggregate.addStudent(stu);
        aggregate.removeStudent(stu);

        StudentIterator iterator = aggregate.getStudentIterator();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("迭代器: hasNext 空列表应返回 false")
    void hasNext_shouldReturnFalseForEmptyList() {
        StudentAggregateImpl aggregate = new StudentAggregateImpl();
        StudentIterator iterator = aggregate.getStudentIterator();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("迭代器: 遍历多个学生应正确")
    void iterator_shouldTraverseAllStudents() {
        StudentAggregateImpl aggregate = new StudentAggregateImpl();
        aggregate.addStudent(new Student("吕阳", "001"));
        aggregate.addStudent(new Student("刘长", "002"));
        aggregate.addStudent(new Student("克莱恩", "003"));
        aggregate.addStudent(new Student("荒天帝", "004"));

        StudentIterator iterator = aggregate.getStudentIterator();
        int count = 0;
        while (iterator.hasNext()) {
            Student student = iterator.next();
            assertThat(student).isNotNull();
            count++;
        }
        assertThat(count).isEqualTo(4);
    }

    @Test
    @DisplayName("迭代器: Student 构造器应正确赋值")
    void student_constructorShouldAssignValues() {
        Student stu = new Student("测试", "999");
        assertThat(stu.getName()).isEqualTo("测试");
        assertThat(stu.getNumber()).isEqualTo("999");
    }
}

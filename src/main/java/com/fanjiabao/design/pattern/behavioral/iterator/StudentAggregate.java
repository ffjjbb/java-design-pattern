package com.fanjiabao.design.pattern.behavioral.iterator;

/**
 * 抽象聚合角色接口
 */
public interface StudentAggregate {

    void addStudent(Student stu);

    void removeStudent(Student stu);

    StudentIterator getStudentIterator();
}


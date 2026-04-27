package com.fanjiabao.design.pattern.behavioral.iterator;

import java.util.List;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/27 14:57
 * @description: 具体迭代器角色类
 */
public class StudentIteratorImpl implements StudentIterator {

    private List<Student> list;

    // 记录遍历时的位置
    private int position = 0;

    public StudentIteratorImpl(List<Student> list) {
        this.list = list;
    }

    public boolean hasNext() {
        return position < list.size();
    }

    public Student next() {
        Student currentStudent = list.get(position);
        position++;
        return currentStudent;
    }

}

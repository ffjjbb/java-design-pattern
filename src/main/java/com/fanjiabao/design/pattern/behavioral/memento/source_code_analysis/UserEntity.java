package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import jakarta.persistence.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:33
 * @description: 实体类 UserEntity
 */
@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Integer age;

    public UserEntity() {
    }

    public UserEntity(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }

    public void setUsername(String username) {
        System.out.println("修改实体 username: " + this.username + " -> " + username);
        this.username = username;
    }

    public void setAge(Integer age) {
        System.out.println("修改实体 age: " + this.age + " -> " + age);
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", username='" + username + "', age=" + age + '}';
    }
}

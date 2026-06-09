package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Hibernate 备忘录模式测试
 * 
 * 测试要点:
 * 1. UserEntity 是 JPA 实体类
 * 2. HibernateMementoService 演示脏检查机制
 * 3. UserRepository 是 JPA 仓储接口
 */
class HibernateMementoTest {

    @Test
    @DisplayName("UserEntity 应有 @Entity 注解")
    void userEntityShouldHaveEntityAnnotation() {
        boolean hasEntityAnnotation = false;
        for (var annotation : UserEntity.class.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Entity")) {
                hasEntityAnnotation = true;
                break;
            }
        }
        assertThat(hasEntityAnnotation)
                .as("UserEntity 应有 @Entity 注解")
                .isTrue();
    }

    @Test
    @DisplayName("UserEntity 应有 @Table 注解")
    void userEntityShouldHaveTableAnnotation() {
        boolean hasTableAnnotation = false;
        for (var annotation : UserEntity.class.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Table")) {
                hasTableAnnotation = true;
                break;
            }
        }
        assertThat(hasTableAnnotation)
                .as("UserEntity 应有 @Table 注解")
                .isTrue();
    }

    @Test
    @DisplayName("UserEntity 应有 id, username, age 属性")
    void userEntityShouldHaveRequiredFields() {
        // 检查属性
        boolean hasId = false, hasUsername = false, hasAge = false;
        for (Field field : UserEntity.class.getDeclaredFields()) {
            switch (field.getName()) {
                case "id" -> hasId = true;
                case "username" -> hasUsername = true;
                case "age" -> hasAge = true;
            }
        }
        assertThat(hasId).as("UserEntity 应有 id 属性").isTrue();
        assertThat(hasUsername).as("UserEntity 应有 username 属性").isTrue();
        assertThat(hasAge).as("UserEntity 应有 age 属性").isTrue();
    }

    @Test
    @DisplayName("UserEntity 应有 getter 和 setter 方法")
    void userEntityShouldHaveGettersAndSetters() throws NoSuchMethodException {
        // 检查 getter 方法
        assertThat(UserEntity.class.getMethod("getId"))
                .as("UserEntity 应有 getId 方法")
                .isNotNull();
        assertThat(UserEntity.class.getMethod("getUsername"))
                .as("UserEntity 应有 getUsername 方法")
                .isNotNull();
        assertThat(UserEntity.class.getMethod("getAge"))
                .as("UserEntity 应有 getAge 方法")
                .isNotNull();

        // 检查 setter 方法
        assertThat(UserEntity.class.getMethod("setUsername", String.class))
                .as("UserEntity 应有 setUsername 方法")
                .isNotNull();
        assertThat(UserEntity.class.getMethod("setAge", Integer.class))
                .as("UserEntity 应有 setAge 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("UserEntity 应有 toString 方法")
    void userEntityShouldHaveToStringMethod() throws NoSuchMethodException {
        assertThat(UserEntity.class.getMethod("toString"))
                .as("UserEntity 应有 toString 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("UserRepository 应存在")
    void userRepositoryShouldExist() {
        assertThat(UserRepository.class)
                .as("UserRepository 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("HibernateMementoService 应存在")
    void hibernateMementoServiceShouldExist() {
        assertThat(HibernateMementoService.class)
                .as("HibernateMementoService 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("HibernateMementoService 应有 createUser, updateUserWithoutSave, verifyUser 方法")
    void hibernateMementoServiceShouldHaveRequiredMethods() {
        Method createUser = null, updateUserWithoutSave = null, verifyUser = null;
        for (Method method : HibernateMementoService.class.getDeclaredMethods()) {
            switch (method.getName()) {
                case "createUser" -> createUser = method;
                case "updateUserWithoutSave" -> updateUserWithoutSave = method;
                case "verifyUser" -> verifyUser = method;
            }
        }
        assertThat(createUser)
                .as("createUser 方法应存在")
                .isNotNull();
        assertThat(updateUserWithoutSave)
                .as("updateUserWithoutSave 方法应存在")
                .isNotNull();
        assertThat(verifyUser)
                .as("verifyUser 方法应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("HibernateMementoRunner 应有 run 方法 (实现 CommandLineRunner)")
    void hibernateMementoRunnerShouldHaveRunMethod() {
        boolean hasRun = false;
        for (Method method : HibernateMementoRunner.class.getMethods()) {
            if (method.getName().equals("run")) {
                hasRun = true;
                break;
            }
        }
        assertThat(hasRun)
                .as("HibernateMementoRunner 应有 run 方法")
                .isTrue();
    }

    @Test
    @DisplayName("HibernateMementoAnalysisApplication 应有 main 方法")
    void hibernateMementoAnalysisApplicationShouldHaveMainMethod() {
        Method main = null;
        try {
            main = HibernateMementoAnalysisApplication.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = HibernateMementoAnalysisApplication.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("HibernateMementoAnalysisApplication 应有 main 方法")
                .isNotNull();
    }
}

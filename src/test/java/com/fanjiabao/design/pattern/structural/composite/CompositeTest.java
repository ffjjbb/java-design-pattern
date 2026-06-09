package com.fanjiabao.design.pattern.structural.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 组合模式单元测试
 */
class CompositeTest {

    @Test
    @DisplayName("组合: MenuItem print 应正常工作")
    void menuItem_shouldPrint() {
        MenuItem item = new MenuItem("页面访问", 3);
        item.print();
    }

    @Test
    @DisplayName("组合: Menu 可添加 MenuItem 和子 Menu")
    void menu_shouldAddChildren() {
        Menu menu = new Menu("系统管理", 1);
        menu.add(new MenuItem("页面访问", 2));
        menu.add(new Menu("子菜单", 2));

        assertThat(menu.getChild(0)).isInstanceOf(MenuItem.class);
        assertThat(menu.getChild(1)).isInstanceOf(Menu.class);
    }

    @Test
    @DisplayName("组合: Menu remove 应移除子节点")
    void menu_shouldRemoveChild() {
        Menu menu = new Menu("系统管理", 1);
        MenuItem item = new MenuItem("页面访问", 2);
        menu.add(item);
        menu.remove(item);

        assertThatThrownBy(() -> menu.getChild(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("组合: MenuItem 调用 add 应抛 UnsupportedOperationException")
    void menuItem_shouldThrowOnAdd() {
        MenuItem item = new MenuItem("test", 1);
        assertThatThrownBy(() -> item.add(new MenuItem("child", 2)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("组合: MenuItem 调用 remove 应抛 UnsupportedOperationException")
    void menuItem_shouldThrowOnRemove() {
        MenuItem item = new MenuItem("test", 1);
        assertThatThrownBy(() -> item.remove(new MenuItem("child", 2)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("组合: 树形结构 print 应递归输出")
    void composite_shouldPrintTreeStructure() {
        MenuComponent component = new Menu("系统管理", 1);
        Menu menu1 = new Menu("菜单管理", 2);
        menu1.add(new MenuItem("页面访问", 3));
        menu1.add(new MenuItem("删除菜单", 3));
        component.add(menu1);

        component.print(); // 递归输出不抛异常
    }

    @Test
    @DisplayName("组合: getName 应返回正确名称")
    void component_shouldReturnCorrectName() {
        Menu menu = new Menu("系统管理", 1);
        MenuItem item = new MenuItem("页面访问", 2);

        assertThat(menu.getName()).isEqualTo("系统管理");
        assertThat(item.getName()).isEqualTo("页面访问");
    }
}

package com.fanjiabao.design.pattern.behavioral.memento;

import com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.BlackMementoPattern;
import com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.RoleStateCaretaker;
import com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.WhiteMementoPattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 备忘录模式单元测试
 */
class MementoTest {

    // ===================== 白箱备忘录 =====================

    @Test
    @DisplayName("白箱备忘录: 初始化状态应为 100/100/100")
    void whiteBox_initStateShouldSetFull() {
        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole();
        role.initState();

        assertThat(role.getVit()).isEqualTo(100);
        assertThat(role.getAtk()).isEqualTo(100);
        assertThat(role.getDef()).isEqualTo(100);
    }

    @Test
    @DisplayName("白箱备忘录: 战斗后状态应为 0/0/0")
    void whiteBox_fightShouldResetStats() {
        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole();
        role.initState();
        role.fight();

        assertThat(role.getVit()).isEqualTo(0);
        assertThat(role.getAtk()).isEqualTo(0);
        assertThat(role.getDef()).isEqualTo(0);
    }

    @Test
    @DisplayName("白箱备忘录: 保存并恢复状态应正确")
    void whiteBox_saveAndRecoverShouldWork() {
        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.GameRole();
        role.initState();

        com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.RoleStateCaretaker caretaker =
                new com.fanjiabao.design.pattern.behavioral.memento.white_box_memento.RoleStateCaretaker();
        caretaker.setRoleStateMemento(role.saveState());

        role.fight();
        assertThat(role.getVit()).isEqualTo(0);

        role.recoverState(caretaker.getRoleStateMemento());
        assertThat(role.getVit()).isEqualTo(100);
        assertThat(role.getAtk()).isEqualTo(100);
        assertThat(role.getDef()).isEqualTo(100);
    }

    @Test
    @DisplayName("白箱备忘录: 完整存档流程应正常执行")
    void whiteBox_fullFlowShouldWork() {
        WhiteMementoPattern wmp = new WhiteMementoPattern();
        wmp.main(null);
    }

    // ===================== 黑箱备忘录 =====================

    @Test
    @DisplayName("黑箱备忘录: 初始化状态应为 100/100/100")
    void blackBox_initStateShouldSetFull() {
        com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole();
        role.initState();

        assertThat(role.getVit()).isEqualTo(100);
        assertThat(role.getAtk()).isEqualTo(100);
        assertThat(role.getDef()).isEqualTo(100);
    }

    @Test
    @DisplayName("黑箱备忘录: 战斗后状态应为 0/0/0")
    void blackBox_fightShouldResetStats() {
        com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole();
        role.initState();
        role.fight();

        assertThat(role.getVit()).isEqualTo(0);
        assertThat(role.getAtk()).isEqualTo(0);
        assertThat(role.getDef()).isEqualTo(0);
    }

    @Test
    @DisplayName("黑箱备忘录: 保存并恢复状态应正确")
    void blackBox_saveAndRecoverShouldWork() {
        com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole role =
                new com.fanjiabao.design.pattern.behavioral.memento.black_box_memento.GameRole();
        role.initState();

        RoleStateCaretaker caretaker = new RoleStateCaretaker();
        caretaker.setMemento(role.saveState());

        role.fight();
        assertThat(role.getVit()).isEqualTo(0);

        role.recoverState(caretaker.getMemento());
        assertThat(role.getVit()).isEqualTo(100);
        assertThat(role.getAtk()).isEqualTo(100);
        assertThat(role.getDef()).isEqualTo(100);
    }

    @Test
    @DisplayName("黑箱备忘录: 完整存档流程应正常执行")
    void blackBox_fullFlowShouldWork() {
        BlackMementoPattern bmp = new BlackMementoPattern();
        bmp.main(null);
    }
}

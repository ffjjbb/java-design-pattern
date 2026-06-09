package com.fanjiabao.design.pattern.behavioral.interpreter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 解释器模式单元测试
 */
class InterpreterTest {

    @Test
    @DisplayName("解释器: Variable interpret 应返回 Context 中的值")
    void variable_shouldReturnValueFromContext() {
        Context context = new Context();
        Variable a = new Variable("a");
        context.assign(a, 10);

        assertThat(a.interpret(context)).isEqualTo(10);
    }

    @Test
    @DisplayName("解释器: Plus 应正确相加")
    void plus_shouldAddCorrectly() {
        Context context = new Context();
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        context.assign(a, 5);
        context.assign(b, 3);

        Plus plus = new Plus(a, b);
        assertThat(plus.interpret(context)).isEqualTo(8);
    }

    @Test
    @DisplayName("解释器: Minus 应正确相减")
    void minus_shouldSubtractCorrectly() {
        Context context = new Context();
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        context.assign(a, 10);
        context.assign(b, 4);

        Minus minus = new Minus(a, b);
        assertThat(minus.interpret(context)).isEqualTo(6);
    }

    @Test
    @DisplayName("解释器: 复合表达式 (a - ((b - c) - d)) 应正确计算")
    void complexExpression_shouldCalculateCorrectly() {
        Context context = new Context();
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable c = new Variable("c");
        Variable d = new Variable("d");

        context.assign(a, 1);
        context.assign(b, 2);
        context.assign(c, 3);
        context.assign(d, 4);

        // a - ((b - c) - d) = 1 - ((2 - 3) - 4) = 1 - (-1 - 4) = 1 - (-5) = 6
        AbstractExpression expression = new Minus(a, new Minus(new Minus(b, c), d));
        int result = expression.interpret(context);

        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("解释器: 简单加法 a + b 应正确")
    void simpleAddition_shouldWork() {
        Context context = new Context();
        Variable a = new Variable("x");
        Variable b = new Variable("y");
        context.assign(a, 7);
        context.assign(b, 3);

        AbstractExpression expr = new Plus(a, b);
        assertThat(expr.interpret(context)).isEqualTo(10);
    }

    @Test
    @DisplayName("解释器: toString 应返回表达式字符串")
    void expression_toStringShouldReturnRepresentation() {
        Variable a = new Variable("a");
        Variable b = new Variable("b");

        Plus plus = new Plus(a, b);
        assertThat(plus.toString()).isEqualTo("(a + b)");

        Minus minus = new Minus(a, b);
        assertThat(minus.toString()).isEqualTo("(a - b)");
    }

    @Test
    @DisplayName("解释器: Variable toString 应返回变量名")
    void variable_toStringShouldReturnName() {
        Variable var = new Variable("test");
        assertThat(var.toString()).isEqualTo("test");
    }
}

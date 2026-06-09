package com.fanjiabao.design.pattern.behavioral.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * 策略模式单元测试
 */
class StrategyTest {

    @Test
    @DisplayName("策略: SalesMan 构造时接收策略")
    void salesMan_shouldAcceptStrategyInConstructor() {
        Strategy strategy = new StrategyA();
        SalesMan salesMan = new SalesMan(strategy);
        assertThat(salesMan.getStrategy()).isSameAs(strategy);
    }

    @Test
    @DisplayName("策略: setStrategy 应替换当前策略")
    void salesMan_shouldSwitchStrategyAtRuntime() {
        SalesMan salesMan = new SalesMan(new StrategyA());
        Strategy strategyB = new StrategyB();
        salesMan.setStrategy(strategyB);
        assertThat(salesMan.getStrategy()).isSameAs(strategyB);
    }

    @Test
    @DisplayName("策略: salesManShow 应委托给策略的 show 方法")
    void salesManShow_shouldDelegateToStrategy() {
        Strategy mockStrategy = mock(Strategy.class);
        SalesMan salesMan = new SalesMan(mockStrategy);
        salesMan.salesManShow();
        verify(mockStrategy, times(1)).show();
    }

    @Test
    @DisplayName("策略: StrategyA show 应正常执行")
    void strategyA_shouldExecuteShow() {
        Strategy strategy = new StrategyA();
        strategy.show();
    }

    @Test
    @DisplayName("策略: StrategyB show 应正常执行")
    void strategyB_shouldExecuteShow() {
        Strategy strategy = new StrategyB();
        strategy.show();
    }

    @Test
    @DisplayName("策略: StrategyC show 应正常执行")
    void strategyC_shouldExecuteShow() {
        Strategy strategy = new StrategyC();
        strategy.show();
    }
}

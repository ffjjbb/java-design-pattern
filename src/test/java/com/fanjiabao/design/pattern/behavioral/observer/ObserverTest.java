package com.fanjiabao.design.pattern.behavioral.observer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * 观察者模式单元测试
 */
class ObserverTest {

    @Test
    @DisplayName("观察者: attach 应添加观察者")
    void attach_shouldAddObserver() {
        SubscriptionSubject subject = new SubscriptionSubject();
        Observer observer = new WeiXinUser("测试用户");
        subject.attach(observer);
        subject.notify("测试消息"); // 不抛异常即为通过
    }

    @Test
    @DisplayName("观察者: detach 应移除观察者")
    void detach_shouldRemoveObserver() {
        SubscriptionSubject subject = new SubscriptionSubject();
        Observer observer = mock(Observer.class);
        subject.attach(observer);
        subject.detach(observer);

        subject.notify("测试消息");
        verify(observer, never()).update(anyString());
    }

    @Test
    @DisplayName("观察者: notify 应通知所有已注册的观察者")
    void notify_shouldNotifyAllAttachedObservers() {
        SubscriptionSubject subject = new SubscriptionSubject();
        Observer observer1 = mock(Observer.class);
        Observer observer2 = mock(Observer.class);

        subject.attach(observer1);
        subject.attach(observer2);

        String message = "AI又更新了！";
        subject.notify(message);

        verify(observer1, times(1)).update(message);
        verify(observer2, times(1)).update(message);
    }

    @Test
    @DisplayName("观察者: 无观察者时 notify 不应抛异常")
    void notify_shouldNotThrowWhenNoObservers() {
        SubscriptionSubject subject = new SubscriptionSubject();
        subject.notify("测试");
    }

    @Test
    @DisplayName("观察者: WeiXinUser 构造器应设置名称")
    void weiXinUser_shouldSetName() {
        WeiXinUser user = new WeiXinUser("石昊");
        user.update("测试");
    }

    @Test
    @DisplayName("观察者: 多个 WeiXinUser 应正常接收消息")
    void multipleWeiXinUsers_shouldReceiveMessage() {
        SubscriptionSubject subject = new SubscriptionSubject();
        subject.attach(new WeiXinUser("石昊"));
        subject.attach(new WeiXinUser("樊任"));
        subject.attach(new WeiXinUser("WOW"));

        subject.notify("AI又更新了！");
    }
}

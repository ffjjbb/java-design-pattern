package com.fanjiabao.design.pattern.creator.factory.extension;

import com.fanjiabao.design.pattern.creator.factory.Coffee;

import java.io.InputStream;
import java.util.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/9 9:33
 * @description: 模式扩展:
 * 简单工厂+配置文件解除耦合
 *  可以通过 '工厂模式+配置文件' 的方式解除工厂对象和产品对象的耦合
 *  在工厂类中加载配置文件中的全类名, 并创建对象进行存储, 客户端如果需要对象, 直接进行获取即可
 */
public class SimpleConfigurationExtension {

    private static Map<String, Coffee> map = new HashMap();

    /**
     * 1.定义配置文件, 编写产品全类名
     * 2.在工厂中读取配置文件获取全类名, 通过反射创建存入Map中, 根据类名获取对象
     */
    static {
        Properties properties = new Properties();
        InputStream is = SimpleConfigurationExtension
                .class
                .getClassLoader()
                .getResourceAsStream("simpleConfigExtensionBean.properties");
        try {
            properties.load(is);
            // 遍历 properties 集合对象
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                // 根据键获取值(全类名)
                String className = properties.getProperty((String) key);
                // 获取字节码对象
                Class clazz = Class.forName(className);
                Coffee obj = (Coffee) clazz.newInstance();
                map.put((String) key, obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Coffee createCoffee(String name) {
        return map.get(name);
    }


    public static void main(String[] args) {
        SimpleConfigurationExtension extension = new SimpleConfigurationExtension();
        Coffee coffee = extension.createCoffee("latte");
        System.out.println(coffee.getName());
    }


    /**
     * 解析 Collection.iterator
     *  Collection 接口是抽象工厂类, ArrayList 是具体的工厂类, Iterator 接口是抽象商品类, ArrayList 类中的 Iter 内部类是具体的商品类。
     *  在具体的工厂类中 iterator() 方法创建具体的商品类的对象。
     */
    public void analysisIterator() {
        List<String> list = new ArrayList<>();
        list.add("java");

        Iterator<String> it = list.iterator();
        while(it.hasNext()) {
            String ele = it.next();
            System.out.println(ele);
        }
    }


}

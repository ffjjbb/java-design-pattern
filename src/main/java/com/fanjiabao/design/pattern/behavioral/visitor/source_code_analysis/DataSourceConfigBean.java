package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

public class DataSourceConfigBean {

    private String name;

    private String url;

    public void setName(String name) {
        System.out.println("执行 setName: " + name);
        this.name = name;
    }

    public void setUrl(String url) {
        System.out.println("执行 setUrl: " + url);
        this.url = url;
    }

    @Override
    public String toString() {
        return "DataSourceConfigBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
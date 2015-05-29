package com.github.knives.spring;

import javax.annotation.PostConstruct;

public class DoublerBean {
    protected int value = 0;

    @PostConstruct
    public void init() {
        System.out.println("postConstructor - DoublerBean");
    }

    @Double
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

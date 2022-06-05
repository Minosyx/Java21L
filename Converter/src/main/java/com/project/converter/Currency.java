package com.project.converter;

public class Currency implements Cloneable {
    private final String name;
    private final String code;

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public Currency clone() throws CloneNotSupportedException {
        return (Currency) super.clone();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

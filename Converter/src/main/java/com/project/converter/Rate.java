package com.project.converter;

public class Rate extends Currency {
    private final double rate;

    public Rate(String code, double rate, String name) {
        super(name, code);
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}

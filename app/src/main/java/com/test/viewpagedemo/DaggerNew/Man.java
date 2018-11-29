package com.test.viewpagedemo.DaggerNew;

import com.test.viewpagedemo.LoggerUtils;

import javax.inject.Inject;

public class Man {

    @Inject
    public Man(Food food) {
        this.food = food;
    }

    Food food;

    public void eat() {
        LoggerUtils.LOGD("eat food");
    }

    public void foodName() {
        LoggerUtils.LOGD("food name = " + food.name);
    }
}

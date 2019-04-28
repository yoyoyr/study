package com.test.viewpagedemo.DaggerNew;

import android.support.annotation.NonNull;

import dagger.Component;

@Component(modules = FoodModule.class)
public interface FoodComponent {

    @NonNull
    Food getFood();

}

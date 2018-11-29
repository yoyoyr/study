package com.test.viewpagedemo.DaggerNew;

import dagger.Component;

@Component(modules = FoodModule.class)
public interface FoodComponent {

    Food getFood();

}

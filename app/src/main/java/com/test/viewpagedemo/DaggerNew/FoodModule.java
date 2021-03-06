package com.test.viewpagedemo.DaggerNew;

import android.support.annotation.NonNull;

import com.test.viewpagedemo.LoggerUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class FoodModule {

    @NonNull
    @Provides
    public Food provideFood() {
        Food food = new Food();
        LoggerUtils.LOGD("provide food " + food.hashCode());
        return food;
    }

}

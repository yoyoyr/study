package com.test.viewpagedemo.DaggerNew;

import dagger.Component;

@ActivityScop
@Component(modules = {CardModule.class}, dependencies = FoodComponent.class)
public interface PlatForm {
    //注入单个变量
    Man getMan();

    Card getCard();

    //给NewDaggerActivity所有需要的对象注入
    void inject(NewDaggerActivity activity);
}

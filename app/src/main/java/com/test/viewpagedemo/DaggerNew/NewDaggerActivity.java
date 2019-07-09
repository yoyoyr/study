package com.test.viewpagedemo.DaggerNew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import dagger.Lazy;

public class NewDaggerActivity extends AppCompatActivity {

    Man man;

    @Inject
    Man man2;

    Man man3;

    @Inject
    Card card;

    @Inject
    Card card2;

    Card card3;

    @Inject
    Provider<Food> food;

    @Inject
    Lazy<String> computer;

    @Inject
    @Named("phone")
    String phone;

    @Inject
    @CarQualifier
    String car;


    @Inject
    public void setMan(Man man) {
        this.man3 = man;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        try {

            PlatForm platForm = DaggerPlatForm.builder()
                    .foodComponent(DaggerFoodComponent.create())
                    .build();

            card3 = platForm.getCard();

            DaggerPlatForm.builder()
                    .foodComponent(DaggerFoodComponent.create())
                    .build().inject(this);

            card.drive();
            LoggerUtils.LOGD("man2 = " + man2.hashCode() + ",man3 = " + man3.hashCode());
            LoggerUtils.LOGD("card = " + card.hashCode() + ",card2 = " + card2.hashCode()
                    + ",card3 = " + card3.hashCode());
            LoggerUtils.LOGD(phone + "-" + computer.get() + "-" + car);
            LoggerUtils.LOGD(food.get().hashCode() + "-" + food.get().hashCode());
        } catch (Exception e) {
            LoggerUtils.LOGE(e);
        }


    }

}

package com.test.viewpagedemo.DaggerNew;

import android.support.annotation.NonNull;

import com.test.viewpagedemo.LoggerUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CardModule {

    @NonNull
    @Provides
    @ActivityScop
    public Card provideCard() {
        Card card = new Card();
        LoggerUtils.LOGD("provide Card " + card.hashCode());
        return card;
    }

    @NonNull
    @Provides
    @Named("phone")
    public String providePhone() {
        return new String("phone");
    }

    @NonNull
    @Provides
    public String provideComputer() {
        return new String("computer");
    }

    @NonNull
    @Provides
    @CarQualifier
    public String provideCar() {
        return new String("car");
    }

}

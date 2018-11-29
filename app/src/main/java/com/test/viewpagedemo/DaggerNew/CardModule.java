package com.test.viewpagedemo.DaggerNew;

import com.test.viewpagedemo.LoggerUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CardModule {

    @Provides
    @ActivityScop
    public Card provideCard() {
        Card card = new Card();
        LoggerUtils.LOGD("provide Card " + card.hashCode());
        return card;
    }

    @Provides
    @Named("phone")
    public String providePhone() {
        return new String("phone");
    }

    @Provides
    public String provideComputer() {
        return new String("computer");
    }

    @Provides
    @CarQualifier
    public String provideCar() {
        return new String("car");
    }

}

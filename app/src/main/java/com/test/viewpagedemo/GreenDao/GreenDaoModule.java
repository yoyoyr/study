package com.test.viewpagedemo.GreenDao;


import android.support.annotation.NonNull;

import com.test.viewpagedemo.GreenDao.resource.DaoSession;
import com.test.viewpagedemo.GreenDao.resource.OrderDao;
import com.test.viewpagedemo.GreenDao.resource.PersonDao;
import com.test.viewpagedemo.GreenDao.resource.UserDao;

import dagger.Module;
import dagger.Provides;

@Module
public class GreenDaoModule {

    @NonNull
    @Provides
    public UserDao provideUserDao(@NonNull DaoSession daoSession) {
        return daoSession.getUserDao();
    }

    @NonNull
    @Provides
    public PersonDao providePersonDao(@NonNull DaoSession daoSession) {
        return daoSession.getPersonDao();
    }

    @NonNull
    @Provides
    public OrderDao provideOrderDap(@NonNull DaoSession daoSession) {
        return daoSession.getOrderDao();
    }
}

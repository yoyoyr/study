package com.test.viewpagedemo.GreenDao;


import com.test.viewpagedemo.GreenDao.resource.DaoSession;
import com.test.viewpagedemo.GreenDao.resource.OrderDao;
import com.test.viewpagedemo.GreenDao.resource.PersonDao;
import com.test.viewpagedemo.GreenDao.resource.UserDao;

import dagger.Module;
import dagger.Provides;

@Module
public class GreenDaoModule {

    @Provides
    public UserDao provideUserDao(DaoSession daoSession) {
        return daoSession.getUserDao();
    }

    @Provides
    public PersonDao providePersonDao(DaoSession daoSession) {
        return daoSession.getPersonDao();
    }

    @Provides
    public OrderDao provideOrderDap(DaoSession daoSession) {
        return daoSession.getOrderDao();
    }
}

package com.test.viewpagedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.test.viewpagedemo.GreenDao.resource.DaoMaster;
import com.test.viewpagedemo.GreenDao.resource.DaoSession;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Context context;
    String dbName;

    public AppModule(Context c, String dbName) {
        context = c;
        this.dbName = dbName;
    }

    @Provides
    public Context provideContext() {
        LoggerUtils.LOGD("provideContext");
        return context;
    }

    /**
     * DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
     * SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
     * DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
     * <p>
     * daoSession = daoMaster.newSession();
     */
    @Provides
    public SQLiteDatabase provideSQLiteDatabase() {
        return new DaoMaster.DevOpenHelper(context, dbName).getWritableDatabase();
    }

    @NonNull
    @Provides
    public DaoMaster provideDaoMaster(SQLiteDatabase sqLiteDatabase) {
        return new DaoMaster(sqLiteDatabase);
    }

    @NonNull
    @Provides
    public DaoSession provideDaoSession(@NonNull DaoMaster daoMaster) {
        LoggerUtils.LOGD("provideDaoSession");
        return daoMaster.newSession();
    }

}

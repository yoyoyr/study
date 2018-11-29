package com.test.viewpagedemo.GreenDao;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.study.point.R;
import com.test.viewpagedemo.DaggerApp;
import com.test.viewpagedemo.GreenDao.resource.DaoSession;
import com.test.viewpagedemo.GreenDao.resource.OrderDao;
import com.test.viewpagedemo.GreenDao.resource.PersonDao;
import com.test.viewpagedemo.GreenDao.resource.UserDao;
import com.test.viewpagedemo.LoggerUtils;

import org.greenrobot.greendao.query.LazyList;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GreenDaoActivity extends AppCompatActivity {

    Unbinder unbinder;

    @Inject
    UserDao userDao;

    @Inject
    PersonDao personDao;

    @Inject
    OrderDao orderDao;

    @Inject
    DaoSession daoSession;

    static Order order;

    int offset;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        unbinder = ButterKnife.bind(this);

        DaggerGreenDaoComponent.builder()
                .appComponent(DaggerApp.getAppComponent()).build()
                .inject(this);

        QueryBuilder.LOG_SQL = QueryBuilder.LOG_VALUES = true;
        offset = 0;
    }

    @OnClick(R.id.query)
    void query() {
        String sql = "select LastName,sum(price) from person " +
                "left join orderTable " +
                "on person.id = orderTable.otherid " +
                "group by LastName " +
                "having sum(price)>0";

        Cursor cursor = daoSession.getDatabase().rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            LoggerUtils.LOGD("last name = " + cursor.getString(0));
            LoggerUtils.LOGD("sum price = " + cursor.getInt(1));
        }
    }

    @OnClick(R.id.queryRxJava)
    public void queryWithLazy(View view) {
        //lazyLoader在get时候才启动游标去读数据库数据
        QueryBuilder queryBuilder = personDao.queryBuilder();
        //通过直接写sql方式
//                List<Person> persons =
        queryBuilder.where(new WhereCondition.StringCondition("LastName = 'yoyo' and City like '%fu%'"))
                .orderAsc(PersonDao.Properties.Year)
                .limit(3).offset(offset)
                .rx().list()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<List<Person>>() {
                    @Override
                    public void call(List<Person> people) {
                        for (int i = 0; i < people.size(); ++i) {
                            LoggerUtils.LOGD(people.get(i).toString());
                        }
                    }

                });

    }

    @OnClick(R.id.queryTwoThread)
    void queryWithSQL() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //lazyLoader在get时候才启动游标去读数据库数据
                QueryBuilder queryBuilder = personDao.queryBuilder();
                LazyList<Person> persons = queryBuilder.where(queryBuilder.and(
                        queryBuilder.or(PersonDao.Properties.LastName.eq("yoyo"), PersonDao.Properties.Year.eq(1990)),
                        PersonDao.Properties.City.like("%fu%")
                )).orderAsc(PersonDao.Properties.Year)
                        .listLazy();

                for (int i = 0; i < persons.size(); ++i) {
                    LoggerUtils.LOGD("lazy list person =" + persons.get(i).toString());
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                offset += 3;
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                //lazyLoader在get时候才启动游标去读数据库数据
                QueryBuilder queryBuilder = personDao.queryBuilder();
                List<Person> persons = queryBuilder.where(queryBuilder.and(
                        queryBuilder.or(PersonDao.Properties.LastName.eq("yoyo"), PersonDao.Properties.Year.eq(1990)),
                        PersonDao.Properties.City.like("%fu%")
                )).orderAsc(PersonDao.Properties.Year)
                        .list();

                for (int i = 0; i < persons.size(); ++i) {
                    LoggerUtils.LOGD("list person = " + persons.get(i).toString());
                }
                offset += 3;
            }
        }).start();
    }

    @OnClick(R.id.queryJoin)
    void queryWithJoin() {
        QueryBuilder queryBuilder = personDao.queryBuilder();
        queryBuilder.join(OrderDao.class, OrderDao.Properties.Otherid)
                .where(OrderDao.Properties.Otherid.eq(PersonDao.Properties.Id));
        List<Person> persons = queryBuilder.list();

        for (int i = 0; i < persons.size(); ++i) {
            LoggerUtils.LOGD(persons.get(i).toString());
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

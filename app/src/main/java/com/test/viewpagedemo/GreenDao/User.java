package com.test.viewpagedemo.GreenDao;

import com.test.viewpagedemo.GreenDao.resource.DaoSession;
import com.test.viewpagedemo.GreenDao.resource.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Entity表面类需要持久化
 */
@Entity(active = true//自动生成update/delete/refresh方法
 )
public class User {
    /**
     * 设置主键自增必须是包装数据类型
     */
    @Id(autoincrement = true)
    Long id;

    @Index(unique = true)
    String key;

    String name;

    int age;

    //    表示该字段不保存到数据库
    @Transient
    String transientData;

    //指定映射到数据库的字段名
    @Property(nameInDb = "PropertyTest")
    String myData;

    @NotNull
    Integer year;
    Integer mounth;
    int day;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;


    @Generated(hash = 586692638)
    public User() {
    }


    @Generated(hash = 324891461)
    public User(Long id, String key, String name, int age, String myData,
            @NotNull Integer year, Integer mounth, int day) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.age = age;
        this.myData = myData;
        this.year = year;
        this.mounth = mounth;
        this.day = day;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMyData() {
        return this.myData;
    }

    public void setMyData(String myData) {
        this.myData = myData;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMounth() {
        return this.mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMounth(Integer mounth) {
        this.mounth = mounth;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}

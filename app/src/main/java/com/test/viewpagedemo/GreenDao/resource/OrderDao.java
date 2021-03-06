package com.test.viewpagedemo.GreenDao.resource;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.test.viewpagedemo.GreenDao.Order.PriceConvert;
import com.test.viewpagedemo.GreenDao.Order.PriceLevel;

import com.test.viewpagedemo.GreenDao.Order;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "orderTable".
*/
public class OrderDao extends AbstractDao<Order, Long> {

    public static final String TABLENAME = "orderTable";

    /**
     * Properties of entity Order.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "oid");
        public final static Property OrderNo = new Property(1, int.class, "orderNo", false, "orderNo");
        public final static Property Otherid = new Property(2, Long.class, "otherid", false, "otherid");
        public final static Property Price = new Property(3, Integer.class, "price", false, "PRICE");
    }

    private final PriceConvert priceConverter = new PriceConvert();
    private Query<Order> person_OrdersQuery;

    public OrderDao(DaoConfig config) {
        super(config);
    }
    
    public OrderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"orderTable\" (" + //
                "\"oid\" INTEGER PRIMARY KEY ," + // 0: id
                "\"orderNo\" INTEGER NOT NULL ," + // 1: orderNo
                "\"otherid\" INTEGER," + // 2: otherid
                "\"PRICE\" INTEGER);"); // 3: price
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"orderTable\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Order entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getOrderNo());
 
        Long otherid = entity.getOtherid();
        if (otherid != null) {
            stmt.bindLong(3, otherid);
        }
 
        PriceLevel price = entity.getPrice();
        if (price != null) {
            stmt.bindLong(4, priceConverter.convertToDatabaseValue(price));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Order entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getOrderNo());
 
        Long otherid = entity.getOtherid();
        if (otherid != null) {
            stmt.bindLong(3, otherid);
        }
 
        PriceLevel price = entity.getPrice();
        if (price != null) {
            stmt.bindLong(4, priceConverter.convertToDatabaseValue(price));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Order readEntity(Cursor cursor, int offset) {
        Order entity = new Order( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // orderNo
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // otherid
            cursor.isNull(offset + 3) ? null : priceConverter.convertToEntityProperty(cursor.getInt(offset + 3)) // price
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Order entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderNo(cursor.getInt(offset + 1));
        entity.setOtherid(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPrice(cursor.isNull(offset + 3) ? null : priceConverter.convertToEntityProperty(cursor.getInt(offset + 3)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Order entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Order entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Order entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "orders" to-many relationship of Person. */
    public List<Order> _queryPerson_Orders(Long otherid) {
        synchronized (this) {
            if (person_OrdersQuery == null) {
                QueryBuilder<Order> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Otherid.eq(null));
                person_OrdersQuery = queryBuilder.build();
            }
        }
        Query<Order> query = person_OrdersQuery.forCurrentThread();
        query.setParameter(0, otherid);
        return query.list();
    }

}

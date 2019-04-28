package com.test.viewpagedemo.GreenDao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "orderTable")
public class Order {

    @Id
    @Property(nameInDb = "oid")
    Long id;

    @Property(nameInDb = "orderNo")
    int orderNo;

    @Property(nameInDb = "otherid")
    Long otherid;

    @Convert(converter = PriceConvert.class, columnType = Integer.class)
    PriceLevel price;

    @Generated(hash = 1854248501)
    public Order(Long id, int orderNo, Long otherid, PriceLevel price) {
        this.id = id;
        this.orderNo = orderNo;
        this.otherid = otherid;
        this.price = price;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public enum PriceLevel {
        LOW, HEIGHT, NORMAL;
    }

    public static class PriceConvert implements PropertyConverter<PriceLevel, Integer> {
        @Nullable
        @Override
        public PriceLevel convertToEntityProperty(@Nullable Integer databaseValue) {
            if (databaseValue != null) {
                if (databaseValue < 100) {
                    return PriceLevel.LOW;
                } else if (databaseValue > 100) {
                    return PriceLevel.HEIGHT;
                } else {
                    return PriceLevel.NORMAL;
                }
            }
            return null;
        }

        @Nullable
        @Override
        public Integer convertToDatabaseValue(@NonNull PriceLevel entityProperty) {
            if (entityProperty.equals(PriceLevel.HEIGHT)) {
                return 110;
            } else if (entityProperty.equals(PriceLevel.LOW)) {
                return 90;
            } else if (entityProperty.equals(PriceLevel.NORMAL)) {
                return 100;
            } else {
                return null;
            }
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }


    public Long getOtherid() {
        return this.otherid;
    }

    public void setOtherid(Long otherid) {
        this.otherid = otherid;
    }

    @NonNull
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", otherid=" + otherid +
                ", price=" + price +
                '}';
    }

    public PriceLevel getPrice() {
        return this.price;
    }

    public void setPrice(PriceLevel price) {
        this.price = price;
    }
}

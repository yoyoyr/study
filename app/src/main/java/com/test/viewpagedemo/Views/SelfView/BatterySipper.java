package com.test.viewpagedemo.Views.SelfView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BatterySipper {
    public int userId;
    public double value;
    public int drainType;
    @Nullable
    public String[] packageNames;

    /**
     * 流量相关
     */
    public long mobileRxBytes;
    public long mobileTxBytes;
    public long wifiRxBytes;
    public long wifiTxBytes;

    public enum DrainType {
        IDLE,
        CELL,
        PHONE,
        WIFI,
        BLUETOOTH,
        FLASHLIGHT,
        SCREEN,
        APP,
        USER,
        UNACCOUNTED,
        OVERCOUNTED
    }

    @NonNull
    @Override
    public String toString() {
        String data = "BatterySipper{" +
                "userId=" + userId +
                ", value=" + value +
                ", drainType=" + drainType;
        if (packageNames != null && packageNames.length > 0) {
            for (int i = 0; i < packageNames.length; ++i) {
                data = data + "package = " + packageNames[i] + "\n";
            }
        }
        data = data + '}';
        return data;
    }
}

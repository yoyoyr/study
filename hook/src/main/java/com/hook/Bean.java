package com.hook;

public class Bean implements Inter{
    Data data;

    @Override
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

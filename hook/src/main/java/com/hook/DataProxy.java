package com.hook;

public class DataProxy extends Data {
    Data data;

    public DataProxy(Data data) {
        this.data = data;
    }

    @Override
    public String say() {
        return "proxy say hello";
    }

    @Override
    public String speak() {
        return data.speak();
    }
}

package com.test.viewpagedemo.EventBus;

import android.support.annotation.NonNull;

public class Event {
    String name;
    String value;

    public Event(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

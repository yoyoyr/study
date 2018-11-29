package com.test.viewpagedemo.EventBus;

public class Event {
    String name;
    String value;

    public Event(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

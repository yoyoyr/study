package com.test.groovy.extension;

public class GradleExtensions {
    String name;
    int age;


    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    int getAge() {
        return age
    }

    void setAge(int age) {
        this.age = age
    }

    @Override
    public String toString() {
        return "GradleExtensions{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
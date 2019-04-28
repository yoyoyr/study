package proxy;

import android.support.annotation.NonNull;

public class Car implements ICar {
    static int count = 1;

    static {
        System.out.println("static car");
    }

    {
        System.out.println("car");
    }

    @NonNull
    @Override
    public String run(String name) {
        return name + " run";
    }

    @Override
    public int add(int a, int b) {
        return 0;
    }
}

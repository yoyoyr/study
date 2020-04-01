package proxy;

import android.support.annotation.NonNull;

public class Car implements ICar {

    @NonNull
    @Override
    public String run(String name) {
        return name + " run";
    }

}

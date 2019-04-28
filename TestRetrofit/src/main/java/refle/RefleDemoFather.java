package refle;

import android.support.annotation.NonNull;

public class RefleDemoFather {

    public String fatherField;


    @NonNull
    public String fatherMethod(String data) {
        return "fatherMethod";
    }
}

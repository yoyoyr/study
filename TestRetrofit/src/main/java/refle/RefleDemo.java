package refle;

import android.support.annotation.NonNull;

public class RefleDemo extends RefleDemoFather {

    public String publicField;

    String field;

    protected String protectedField;

    private String privateField;


    @NonNull
    public String publicMethod(String data) {
        return "publicMethod";
    }


    @NonNull
    public String method(String data) {
        return "method";
    }


    @NonNull
    public String protectedMethod(String data) {
        return "protectedMethod";
    }


    @NonNull
    public String privateMethod(String data) {
        return "privateMethod";
    }
}

package refle;

public class RefleDemo extends RefleDemoFather {

    public String publicField;

    String field;

    protected String protectedField;

    private String privateField;


    public String publicMethod(String data) {
        return "publicMethod";
    }


    public String method(String data) {
        return "method";
    }


    public String protectedMethod(String data) {
        return "protectedMethod";
    }


    public String privateMethod(String data) {
        return "privateMethod";
    }
}

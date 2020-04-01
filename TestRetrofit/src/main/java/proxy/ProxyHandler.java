package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    Object object;

    public ProxyHandler(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        System.out.println("before invoke");
        Object ret = method.invoke(object, objects);
        System.out.println("return " + ret);
        System.out.println("after invoke");
        return null;
    }
}

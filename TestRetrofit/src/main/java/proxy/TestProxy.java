package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy {

    public static void main(String[] args) {
        //静态代理
//        ICar car = new Car();
//        ICar carProxy = new StaticProxyCar(car);
//        System.out.println(carProxy.run("yoyo"));

        //动态代理
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Object proxyCar = Proxy.newProxyInstance(ICar.class.getClassLoader(), new Class[]{ICar.class, ICalc.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method = " + method.getName());
//                会导致递归调用，因此只能是接口
//                method.invoke(proxy, args);
                if ("run".equals(method.getName())) {
                    System.out.println("proxy run");
                    String name = (String) args[0];
                    System.out.println("name = " + name);
                    return "yoyo name is " + name;
                } else if ("add".equals(method.getName())) {
                    System.out.println("proxy add");
                    int a = (int) args[0];
                    int b = (int) args[1];
                    return a + b;
                } else if ("count".equals(method.getName())) {
                    System.out.println("proxy count");
                    int a = (int) args[0];
                    int b = (int) args[1];
                    return a + b + 1;
                }
                return null;
            }
        });
//        System.out.println("proxy card = " + proxyCar);
        System.out.println(((ICar) proxyCar).run("yoyo"));
        System.out.println(((ICar) proxyCar).add(1, 2));
        System.out.println(((ICalc) proxyCar).count(1, 2));

        System.out.println("-------------------------------------------------");
        try {
            Class clzz = Class.forName("proxy.Car");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

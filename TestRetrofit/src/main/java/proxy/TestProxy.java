package proxy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestProxy {
    static final long ONE_DAY = 86400000;

    public static void main(String[] args) throws ParseException {
//        proxy();

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        String year = c.get(Calendar.YEAR) + "";
        int v1 = c.get(Calendar.MONTH) + 1;
        String month = v1 + "";
        if (v1 < 10) {
            month = "0" + v1;
        }
        int v2 = c.get(Calendar.DATE);
        String date = v2 + "";
        if (v2 < 10) {
            date = "0" + v2;
        }
        String time = year + "-" + month + "-" + date + " 22";
        long before22 = new SimpleDateFormat("yyyy-MM-dd HH").parse(time).getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long today = dateFormat.parse(year + "-" + month + "-" + date + " 23:59").getTime();
        long mills;
//        if (System.currentTimeMillis() >= before22) {
            mills = today + 2 * ONE_DAY;
//        } else {
//            mills = today + ONE_DAY;
//        }

        String data =  new SimpleDateFormat("MM-dd HH:mm").format(mills);
        System.out.println("before22 = " + before22 + ",today = " + today+",data = "+data);

        if (System.currentTimeMillis() >= before22) {
            System.out.println("two data ");
        } else {
            System.out.println("one data ");
        }
    }

    private static void proxy() {
        //静态代理
//        ICar car = new Car();
//        ICar carProxy = new StaticProxyCar(car);
//        System.out.println(carProxy.run("yoyo"));

        //动态代理
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Object proxyCar = Proxy.newProxyInstance(ICar.class.getClassLoader(), new Class[]{ICar.class, ICalc.class}, new InvocationHandler() {
            @Nullable
            @Override
            public Object invoke(Object proxy, @NonNull Method method, Object[] args) throws Throwable {
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

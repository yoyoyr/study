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
import java.util.HashMap;

import javax.swing.Icon;

public class TestProxy {
    static final long ONE_DAY = 86400000;

    public static void main(String[] args) throws ParseException {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ICar car = new Car();
        InvocationHandler invocationHandler = new ProxyHandler(car);
        ICar proxyCar = (ICar) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(), invocationHandler);
        proxyCar.run("levin");





    }

}

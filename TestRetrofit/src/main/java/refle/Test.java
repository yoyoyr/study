package refle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射给程序员提供了站在jvm角度编程的视角
 * 反射的动态创建特性，也提供了一种解耦的思路(⊙o⊙)
 * 反射是非常规开发手段，它会抛弃 Java 虚拟机的很多优化，
 * 所以同样功能的代码，反射要比正常方式要慢，所以考虑到采用反射时，要考虑它的时间成本
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
//        获取类的class对象
//        Class refle =  RefleDemo.class;//返回jvm真正指向方法区的类 对象
//        Class refle =  new RefleDemo().getClass();//仅仅装载不初始  化
        Class refle = Class.forName("refle.RefleDemo");//初始化并装载类

//        获取类名
        System.out.println("class name = " + refle.getName());
//        获取类修饰符
        System.out.println("class identify = " + Modifier.toString(refle.getModifiers()));
        System.out.println("-----------------------------");


//        获取public属性，包括父类
        Field[] fields = refle.getFields();
        for (int i = 0; i < fields.length; ++i) {
            System.out.println("getFields() = " + fields[i].getName());
        }
//        获取所有属性，不包括父类
        fields = refle.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            System.out.println("getDeclaredFields() = " + fields[i].getName());
        }
        System.out.println("-----------------------------");

        //        获取public方法，包括父类
        Method[] methods = refle.getMethods();
        for (int i = 0; i < fields.length; ++i) {
            System.out.println("getMethods() = " + methods[i].getName());
        }
//        获取所有方法，不包括父类
        methods = refle.getDeclaredMethods();
        for (int i = 0; i < fields.length; ++i) {
            System.out.println("getDeclaredMethods() = " + methods[i].getName());
        }
        System.out.println("-----------------------------");

    }

}
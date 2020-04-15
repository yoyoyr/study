package refle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.relation.Relation;

/**
 * 反射给程序员提供了站在jvm角度编程的视角
 * 反射的动态创建特性，也提供了一种解耦的思路(⊙o⊙)
 * 反射是非常规开发手段，它会抛弃 Java 虚拟机的很多优化，
 * 所以同样功能的代码，反射要比正常方式要慢，所以考虑到采用反射时，要考虑它的时间成本
 */
public class Test {

    private static boolean INIT_RESULT = true;

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

//        refle();
//        linkedTest();

        for (int i = 0; i < 5; i++) {
            if (i % 7 == 6)
                INIT_RESULT &= false;
        }
        System.out.println(INIT_RESULT);
    }

    private static void refle() throws ClassNotFoundException {
        //        获取类的class对象
//        Class refle =  RefleDemo.class;//返回jvm真正指向方法区的类 对象
//        Class refle =  new RefleDemo().getClass();//仅仅装载不初始  化
        Class refle = Class.forName("refle.RefleDemo");//初始化并装载类

//        获取类名
        System.out.println("class name = " + refle.getName());
//        获取类修饰符
        System.out.println("class identify = " + Modifier.toString(refle.getModifiers()));
        System.out.println("-----------------------------");

        ;

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

        int end;
        for (end = 1; end < 10; end++) {

        }
        System.out.println("-----------------------------" + end);

    }

    private static void linkedTest() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");

//        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

        System.out.println("以下是accessOrder=true的情况:");

        map = new LinkedHashMap<String, String>(10, 0.75f, true);
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.get("3");//2移动到了内部的链表末尾
//        map.get("4");//4调整至末尾
//        map.put("3", "e");//3调整至末尾
//        map.put(null, null);//插入两个新的节点 null
//        map.put("5", null);//5
        Map.Entry toEvict = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            toEvict = entry;
        }

        System.out.println(toEvict.getKey() + "--" + toEvict.getValue());

    }

}
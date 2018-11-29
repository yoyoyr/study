package Annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射方式
 * CGLib方式
 */

@TypeAnnotation(name = "test")
public class Test {

    @FiledAnnotation(name = "a")
    public String a;

    public static void main(String[] args) {

        if (Test.class.isAnnotationPresent(TypeAnnotation.class)) {
            TypeAnnotation typeAnnotation = Test.class.getAnnotation(TypeAnnotation.class);
            System.out.println(typeAnnotation.name());
        }

        try {
            Field field = Test.class.getField("a");
            field.setAccessible(true);
            FiledAnnotation filedAnnotation = field.getAnnotation(FiledAnnotation.class);
            System.out.println(filedAnnotation.name());

            Method method = Test.class.getDeclaredMethod("say", String.class);
            MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
            System.out.println(methodAnnotation.count() + "---" + methodAnnotation.name());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @MethodAnnotation(name = "say", count = 3)
    private static void say(String say) {
        System.out.println(say);
    }
}
package Generic;

import java.util.ArrayList;
import java.util.List;

public class Generic {

    public static void main(String[] args) {
        List<Object> obj = new ArrayList<>(2);
        obj.add(new Dog());
        obj.add(new Integer(1));
        obj.add(new Integer(1));
        Object o = obj.get(0);
//        showKey(obj);
    }


    private static <T extends Animal> void showKey(T obj) {
//        obj.add(new Animal());
//        for (Animal a : obj) {
//            System.out.println(a.getType());
//        }
    }
}

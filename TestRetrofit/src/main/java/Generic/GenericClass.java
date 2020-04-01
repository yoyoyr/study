package Generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//泛型类
public class GenericClass<T> {
    private T value;

    public T getValue() {
        return value;
    }

    //泛型方法
    public <E> void GenericFunc(E e) {

    }

    public void test() {

        //自动推断类型
        List<Cat> cats = new ArrayList<>(3);
        //无法向上转型成List<Animal>,因此无法添加Animal，泛型中的T不能变
//        cats.add(new Animal());

    }

    //假设使用时传入List<Dog>,此时不能往里面加Cat或者Animal类型。
    //extends的另外一个用途是修饰类时，<T extends Animal>，然后使用T类型
    //所以，extends修饰类时<T extends Animal表示，T限定为Animal或者其子类。
    // 修饰方法时List<? extends Animal>表示除了集合限定类型以外，不能加入元素
    public void testExtends(List<? extends Animal> animals) {

        //可以传入cat，dog和animal
        for (Animal a : animals) {
            a.getType();
        }
        //无法添加
//        animals.add(new Animal());
    }


    public void testSuper(List<? super Animal> dogs) {

        dogs.add(new Dog());
        dogs.add(new Animal());
        dogs.add(new Cat());
        //获取类型只能是Object
//        Animal animal = dogs.get(0);
        Object o = dogs.get(0);

    }


    //无限定通配符，包含了extends和super的特性
    public void testo(List<?> animals) {
        //无法添加任何参数
//        animals.add(new Object());
        Object o = animals.get(0);
        //只能取出Object类型
//        Animal a = animals.get(0);
    }

    //实例化T必须借助Class<T>
    public void newClass(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        clazz.newInstance();
    }
}
package proxy;

public class Car implements ICar {
    static int count = 1;

    static {
        System.out.println("static car");
    }

    {
        System.out.println("car");
    }

    @Override
    public String run(String name) {
        return name + " run";
    }

    @Override
    public int add(int a, int b) {
        return 0;
    }
}

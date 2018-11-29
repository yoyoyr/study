package proxy;

public class StaticProxyCar implements ICar {

    ICar iCar;

    public StaticProxyCar(ICar iCar) {
        this.iCar = iCar;
    }

    @Override
    public String run(String name) {
        System.out.println("static proxy car");
        return iCar.run(name);
    }

    @Override
    public int add(int a, int b) {
        return 0;
    }
}

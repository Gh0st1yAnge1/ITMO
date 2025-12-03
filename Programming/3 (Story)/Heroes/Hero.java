package Heroes;

public class Hero{
    protected int age;
    protected double weight;
    protected double height;
    protected String name;

    public Hero(int age, double weight, double height, String name){
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.name = name;
    }

    @Override
    public String toString() {
        return "hero";
    }
}

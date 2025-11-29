package Heroes;

import Dishes.*;

public class Witch extends Hero{
    protected int age;
    protected double weight;
    protected double height;

    public Witch(int age, double weight, double height){
        super(age, weight, height);
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "witch";
    }
}

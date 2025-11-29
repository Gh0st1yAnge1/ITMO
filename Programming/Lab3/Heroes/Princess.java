package Heroes;

import Dishes.*;

import java.util.ArrayList;

public class Princess extends Hero{
    protected int age;
    protected double weight;
    protected double height;

    public Princess(int age, double weight, double height){
        super(age, weight, height);
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "princess";
    }
}

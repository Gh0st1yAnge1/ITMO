package Heroes;

import Dishes.*;

import java.util.ArrayList;

public class Hero{
    protected int age;
    protected double weight;
    protected double height;
    protected ArrayList<Dishes> dishes = new ArrayList<>();

    public Hero(int age, double weight, double height){
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public ArrayList<Dishes> getSetOfDishes(Plate plate, Fork fork, Spoon spoon, Knife knife, Glass glass){
        dishes.add(plate); dishes.add(fork); dishes.add(spoon); dishes.add(knife); dishes.add(glass);
        return dishes;
    }

    @Override
    public String toString() {
        return "hero got today: " + dishes;
    }
}

package Heroes;

import Dishes.*;

import java.util.ArrayList;

public class Enchantress extends Hero{
    protected int age;
    protected double weight;
    protected double height;
    private static int numberOfEnchantresses = 0;
    protected ArrayList<Dishes> dishes = new ArrayList<>();

    public Enchantress(int age, double weight, double height){
        super(age, weight, height);
        this.age = age;
        this.weight = weight;
        this.height = height;
        numberOfEnchantresses += 1;
    }

    public ArrayList<Dishes> getSetOfDishes(Plate plate, Fork fork, Spoon spoon, Knife knife, Glass glass){
        dishes.add(plate); dishes.add(fork); dishes.add(spoon); dishes.add(knife); dishes.add(glass);
        return dishes;
    }

    @Override
    public String toString() {
        String dish = new String();
        for (Dishes d: dishes){
            dish += d + " ";
        }
        return "Еnchantress number " + numberOfEnchantresses + " is got: " + dish;
    }
}

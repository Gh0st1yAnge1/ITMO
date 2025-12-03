package Heroes;

import Dishes.*;
import Gifts.Gift;
import Interface.Appearance;

public class Enchantress extends Hero implements Appearance {
    protected String setOfDishes;
    protected Gift giftForPrincess;

    public Enchantress(int age, double weight, double height, String name){
        super(age, weight, height, name);
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.name = name;
    }

    @Override
    public void methodOfAppearance() {
        System.out.println(" appeared with a clap.");
    }

    public void makeGift(Gift gift){
        giftForPrincess = gift;
    }

    public void giveGift(){
        System.out.println(name + " prepared " + giftForPrincess + " as a gift and as a wish.");
        System.out.println("She said 'I give u the " + giftForPrincess.gift() + " and wish u that " + giftForPrincess.quality()
                + " will become your best quality.'");
        System.out.print("The " + giftForPrincess.gift());
        giftForPrincess.methodOfAppearance();
    }

    public void makeSetOfDishes(Plate plate, Fork fork, Spoon spoon, Knife knife, Glass glass){
        setOfDishes =  plate + ", " + fork + ", " + spoon + ", " + knife + ", " + glass;
    }

    public String getSetOfDishes(){
        return setOfDishes;
    }

    @Override
    public String toString() {
        return "Enchantress " + name;
    }
}

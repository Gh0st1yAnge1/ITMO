package Heroes;

import Dishes.*;
import Gifts.Gift;
import Interface.Appearance;

public class Enchantress extends Hero implements Appearance {
    protected String setOfDishes;
    protected Gift giftForPrincess;
    protected Dinner dinnerDish;

    public Enchantress(Names name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
    }

    @Override
    public void showEmotion() {
        super.showEmotion();
    }

    @Override
    public void methodOfAppearance() {
        System.out.println(" appeared with a clap.");
    }

    public void makeDinner(Dinner dinner){
        dinnerDish = dinner;
    }

    public void getDinner(){
        System.out.println("Then, she decided to taste " + dinnerDish + " ... and was " + emotion + "!");
    }

    public void makeGift(Gift gift){
        giftForPrincess = gift;
    }

    public void giveGift(){
        System.out.println(name + " prepared " + giftForPrincess + " as a gift and as a wish.");
        System.out.println("She said: 'I give u the " + giftForPrincess.gift() + " and wish u that " + giftForPrincess.quality()
                + " will become your best quality.'");
        System.out.print("The " + giftForPrincess.gift());
        giftForPrincess.methodOfAppearance();
    }

    public void makeSetOfDishes(Plate plate, Fork fork, Spoon spoon, Knife knife, Glass glass){
        setOfDishes =  plate + ", " + fork + ", " + spoon + ", " + knife + ", " + glass;
    }

    public void getSetOfDishes(){
        System.out.print("There were " + setOfDishes + " in front of the " + name + ".\n");;
    }

    @Override
    public String toString() {
        return "Enchantress " + name;
    }
}

package Heroes;

import Dishes.*;
import Gifts.*;
import Interfaces.Invite;
import Interfaces.MakeGift;

public class Enchantress extends Hero implements Invite, MakeGift {

    public Enchantress(String name){
        super(name);
        this.name = name;
    }

    @Override
    public  void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    @Override
    public void showEmotion() {
        super.showEmotion();
    }

    @Override
    public Gift makeGift(Toys toy, Qualities qualitiy) {
        return MakeGift.super.makeGift(toy, qualitiy);
    }

    @Override
    public void beInvited() {
        System.out.print(name); Invite.super.beInvited();
    }

    public void getSetOfDishes(SetOfDishes setOfDishes){
        System.out.println(name + " got " + setOfDishes);
    }

    public void getDinner(DinnerDishes dinnerDishes){
        System.out.println(name + " got " + dinnerDishes);
    }

    @Override
    public String toString() {
        return "Enchantress " + name;
    }
}

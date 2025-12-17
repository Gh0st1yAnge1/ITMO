package heroes;

import dishes.*;
import exceptions.DoNotLikeGift;
import exceptions.GiftOverflow;
import gifts.Gift;
import gifts.Toys;
import interfaces.Invite;
import interfaces.Makegift;
import interfaces.Reciever;

public class Enchantress extends Hero implements Invite, Makegift{

    private String name;
    private Emotion emotion;

    public Enchantress(String name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
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
        return "Enchantress " + name + " feels " + emotion;
    }

    @Override
    public Gift makeGift(Gift gift, Reciever hero) throws DoNotLikeGift, GiftOverflow{
        if (gift.toy() == Toys.TOY_CASTLE || gift.toy() == Toys.DRESS){
            throw new DoNotLikeGift();
        }
        return hero.recieveGift(gift);
    }

}

import Dishes.*;
import Heroes.*;
import Gifts.*;
import Interface.*;

import java.util.ArrayList;

public class StoryText {
    ArrayList<Enchantress> enchantresses = new ArrayList<>();
    Princess princess = new Princess(Names.ALICE);
    Witch witch = new Witch(Names.DELAYLA);


    public void preparing(){

        Names[] names = {Names.SIDNEY, Names.KATRIN, Names.JENNY, Names.HANNA, Names.MARY, Names.JUDY, Names.SHAIN};
        princess.setEmotion();
        witch.setEmotion();

        for (int i = 0; i < 7; i++){
            Enchantress e = new Enchantress(names[i]);
            e.setEmotion();
            e.makeDinner();
            e.prepareToyAndWish();
            e.makeGift();
            e.makeSetOfDishes();
            enchantresses.add(e);
        }
    }

    public void part1(){
        System.out.println("There was a " + princess + "'s " + "christines in the kingdom.");
        System.out.println("7 enchantresses were invited to celebrate this event, wish a lot of goods and give some gifts.");
        System.out.print("They weren't in need to walk, because they can do magic!");
        for (Enchantress e: enchantresses){
            System.out.println("\n");
            System.out.println(e.methodOfAppearance());
            System.out.println(e.giveGift());
        }
        System.out.println("\n");
        System.out.println("After recieving all gifts " + princess.showEmotion());
    }

    public void part2(){
        System.out.println("After the " + princess + "'s christines all the enchantresses went to a gala dinner in honor of this momentous event, where sumptuous treats were prepared for them.");
        for (Enchantress e: enchantresses){
            System.out.println("\n");
            System.out.println(e.getSetOfDishes());
            System.out.println(e.getDinner());
            System.out.println(e.showEmotion());
        }
        System.out.println("\n");
        System.out.println("Then the dinner was continuing, but....");
        System.out.println("\n");
    }

    public void part3(){
        System.out.println(witch.methodOfAppearance());
        System.out.print("She was ");
        System.out.print(witch.showEmotion());
        System.out.println(" because could miss the party and nobody cared about her.");
        System.out.println("She wasn't invited, because all thought that she dead lot's of years ago or was enchanted.");
        System.out.println("More than 50 years she was sitting inside her tower, and naturally everyone forgot about her.");
    }

    @Override
    public String toString() {
        return "This is a story text:\n";
    }
}
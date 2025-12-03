import Dishes.*;
import Heroes.*;
import Gifts.*;
import Interface.*;
import java.util.ArrayList;

public class StoryText {
    ArrayList<Enchantress> enchantresses = new ArrayList<>();
    Princess princess = new Princess(1, 7.8, 40.6, Names.Alice.toString());
    Witch witch = new Witch(66, 76.3, 168.4, Names.Delayla.toString());

    public void preparing(){

        Material[] material = {Material.golden, Material.crystal, Material.porcelain, Material.silver};
        PreciousStones[] stones = {PreciousStones.brilliants, PreciousStones.emeralds, PreciousStones.rubies, PreciousStones.sapphires};
        Gifts[] gifts = {Gifts.doll, Gifts.dress, Gifts.book_of_fairy_tales, Gifts.box_of_sweets,
                Gifts.teddy_bear, Gifts.toy_castle, Gifts.toy_princess};
        Qualities[] qualities = {Qualities.beauty, Qualities.courage, Qualities.determination, Qualities.endurance,
        Qualities.intelligence, Qualities.kindness, Qualities.politness};
        Names[] names = {Names.Hanna, Names.Judy, Names.Jenny, Names.Katrin, Names.Mary, Names.Shain, Names.Sidney};

        for (int i = 0; i < 7; i++){
            Enchantress e = new Enchantress(18 + i%3, 56.6 + 0.33*i, 167.3 - 2*(i%3), names[i].toString());

            Plate plate = new Plate(material[i%4], stones[i%4]);
            Fork fork = new Fork(material[(i+1)%4]);
            Spoon spoon = new Spoon(material[(i+2)%4]);
            Knife knife = new Knife(material[i%4]);
            Glass glass = new Glass(material[(i+3)%4]);
            e.makeSetOfDishes(plate, fork, spoon, knife, glass);

            Gift gift = new Gift(gifts[i%7], qualities[i%7]);
            e.makeGift(gift);

            enchantresses.add(e);
        }
    }

    public void part1(){
        System.out.println("There was a " + princess + "'s " + "christines in the kingdom.");
        System.out.println("7 enchantresses were invited to celebrate this event, wish a lot of goods and give some gifts.");
        System.out.print("They weren't in need to walk, because they can do magic! So that, they");
        enchantresses.getFirst().methodOfAppearance();
        System.out.print("Little " + princess + " was amazed and started laughing with pleasure!");
        for (Enchantress e: enchantresses){
            System.out.println("\n");
            e.giveGift();
        }
    }

    public void part2(){
        System.out.println("smth");
    }

    public void part3(){
        System.out.println("smth");
    }

    @Override
    public String toString() {
        return "This is a story text:\n";
    }
}
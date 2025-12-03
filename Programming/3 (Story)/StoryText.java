import Dishes.*;
import Heroes.*;
import Gifts.*;
import Interface.*;
import java.util.ArrayList;

public class StoryText {
    ArrayList<Enchantress> enchantresses = new ArrayList<>();
    Princess princess = new Princess(Names.Alice, Emotion.astonished);
    Witch witch = new Witch(Names.Delayla, Emotion.stunned);


    public void preparing(){

        Material[] material = {Material.golden, Material.crystal, Material.porcelain, Material.silver};
        PreciousStones[] stones = {PreciousStones.brilliants, PreciousStones.emeralds, PreciousStones.rubies, PreciousStones.sapphires};
        Gifts[] gifts = {Gifts.doll, Gifts.dress, Gifts.book_of_fairy_tales, Gifts.box_of_sweets,
                Gifts.teddy_bear, Gifts.toy_castle, Gifts.toy_princess};
        Qualities[] qualities = {Qualities.beauty, Qualities.courage, Qualities.determination, Qualities.endurance,
                Qualities.intelligence, Qualities.kindness, Qualities.politness};
        Names[] names = {Names.Hanna, Names.Judy, Names.Jenny, Names.Katrin, Names.Mary, Names.Shain, Names.Sidney};
        Dinner[] dinners = {Dinner.cold_roast_beef_and_horseradish_rolls, Dinner.eton_mess, Dinner.pigs_in_blankets, Dinner.coronation_chicken,
                Dinner.salmon_and_cucumber_sandwiches, Dinner.strawberries_and_cream, Dinner.yorkshire_pudding};
        Emotion[] emotions = {Emotion.amazed, Emotion.amused, Emotion.delighted, Emotion.ecstatic,
                Emotion.overjoyed, Emotion.thrilled, Emotion.shocked};

        for (int i = 0; i < 7; i++){
            Enchantress e = new Enchantress(names[i], emotions[i]);

            Plate plate = new Plate(material[i%4], stones[i%4]);
            Fork fork = new Fork(material[(i+1)%4]);
            Spoon spoon = new Spoon(material[(i+2)%4]);
            Knife knife = new Knife(material[i%4]);
            Glass glass = new Glass(material[(i+3)%4]);
            e.makeSetOfDishes(plate, fork, spoon, knife, glass);

            Gift gift = new Gift(gifts[i%7], qualities[i%7]);
            e.makeGift(gift);
            e.makeDinner(dinners[i]);
            enchantresses.add(e);
        }
    }

    public void part1(){
        System.out.println("There was a " + princess + "'s " + "christines in the kingdom.");
        System.out.println("7 enchantresses were invited to celebrate this event, wish a lot of goods and give some gifts.");
        System.out.print("They weren't in need to walk, because they can do magic! So that, they");
        enchantresses.getFirst().methodOfAppearance();
        System.out.print("Little " + princess + " was ");
        princess.showEmotion();
        System.out.println(" and laughed with pleasure!");
        for (Enchantress e: enchantresses){
            System.out.println("\n");
            e.giveGift();
        }
        System.out.println("\n");
    }

    public void part2(){
        System.out.println("After the " + princess + "'s christines all the enchantresses went to a gala dinner in honor of this momentous event, where sumptuous treats were prepared for them.");
        for (Enchantress e: enchantresses){
            System.out.println("\n");
            e.getSetOfDishes();
            e.getDinner();
        }
        System.out.println("\n");
        System.out.println("Then the dinner was continuing, but....");
        System.out.println("\n");
    }

    public void part3(){
        witch.methodOfAppearance();
        System.out.print("She was ");
        witch.showEmotion();
        System.out.println(" because could miss the party and nobody cared about her.");
        System.out.println("She wasn't invited, because all thought that she dead lot's of years ago or was enchanted.");
        System.out.println("More than 50 years she was sitting inside her tower, and naturally everyone forgot about her.");
    }

    @Override
    public String toString() {
        return "This is a story text:\n";
    }
}
import Dishes.*;
import Gifts.*;                                    //на части было разбито, потому что там был текст истории (part1() ,part2(), part3())
import Heroes.*;                                   //если нужно, я его добавлю, либо все выводы сделаю в одном методе printStory()
import Interfaces.*;                               //но, думаю, что сам текст истории тут никакой роли не играет)

import java.util.ArrayList;                        //массивы с енамами сделаны, чтобы вручную каждой волшебнице
                                                   //не добавлять предметы
public class StoryText {
    ArrayList<Enchantress> enchantresses = new ArrayList<>();
    Princess princess = new Princess("Alice");
    Witch witch = new Witch("Delayla");


    public void preparing(){
        String[] names = {"Sidney", "Katrin", "Jenny", "Hanna", "Mary", "Judy", "Shain"};
        Emotion[] emotions = {Emotion.AMAZED, Emotion.ASTONISHED, Emotion.DELIGHTED, Emotion.ECSTATIC, Emotion.OVERJOYED, Emotion.SHOCKED, Emotion.THRILLED};
        for (int i = 0; i < 7; i++){
            Enchantress e = new Enchantress(names[i]);
            e.setEmotion(emotions[i]);
            enchantresses.add(e);
        }
    }

    public void part1(){
        Toys[] toys = {Toys.TOY_CASTLE, Toys.TOY_PRINCESS, Toys.DOLL, Toys.DRESS, Toys.BOOK_OF_FAIRY_TALES, Toys.BOX_OF_SWEETS, Toys.TEDDY_BEAR};
        Qualities[] qualities = {Qualities.BEAUTY, Qualities.COURAGE, Qualities.DETERMINATION, Qualities.ENDURANCE, Qualities.INTELLIGENCE, Qualities.KINDNESS, Qualities.POLITENESS};
        for (int i = 0; i < 7; i++){
            enchantresses.get(i).beInvited();
            princess.recieveGift(enchantresses.get(i).makeGift(toys[i], qualities[i]));
            System.out.println("\n");
        }
        princess.setEmotion(Emotion.AMUSED);
        princess.showEmotion();
    }

    public void part2(){
        Material[] materials = {Material.CRYSTAL, Material.GOLDEN, Material.PORCELAIN, Material.SILVER};
        PreciousStones[] stones = {PreciousStones.BRILLIANTS, PreciousStones.EMERALDS, PreciousStones.RUBIES, PreciousStones.SAPPIRES};
        DinnerDishes[] dinnerDishes = {DinnerDishes.PIGS_IN_BLANKETS, DinnerDishes.CORONATION_CHICKEN, DinnerDishes.ETON_MESS,
                DinnerDishes.YORKSHIRE_PUDDING, DinnerDishes.COLD_ROAST_BEEF_AND_HORSERADISH_ROLLS, DinnerDishes.SALMON_AND_CUCUMBER_SANDWICHES, DinnerDishes.STRAWBERRIES_AND_CREAM};
        for (int i = 0; i < 7; i++){
            enchantresses.get(i).getSetOfDishes(new SetOfDishes(new Plate(materials[i%4], stones[i%4]),
                    new Fork(materials[i%4]), new Spoon(materials[i%4]), new Knife(materials[i%4]), new Glass(materials[i%4])));
            enchantresses.get(i).getDinner(dinnerDishes[i]);
            enchantresses.get(i).showEmotion();
            System.out.println("\n");
        }

    }

    public void part3(){
        witch.setEmotion(Emotion.STUNNED);
        witch.showEmotion();
    }

    @Override
    public String toString() {
        return "This is a story text:\n";
    }
}
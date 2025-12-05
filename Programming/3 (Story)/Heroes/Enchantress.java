package Heroes;

import Dishes.*;
import Gifts.*;
import Interface.Appearance;

public class Enchantress extends Hero implements Appearance {

    protected Toys toyForPrincess;
    protected Qualities qualityForPrincess;          //игрушки и пожелания принцессе
    protected Gift giftForPrincess;

    protected String setOfDishes;
    protected DinnerDishes dinnerDish;
    protected Plate plate;
    protected Fork fork;                             //посуда и блюдо, которое получила волшебница
    protected Spoon spoon;
    protected Knife knife;
    protected Glass glass;

    protected static int numberOfEnchantresses;

    public Enchantress(Names name){
        super(name);
        this.name = name;
        numberOfEnchantresses += 1;
    }

    @Override
    public void setEmotion() {
        super.setEmotion();
    }

    @Override
    public String showEmotion() {
        return super.showEmotion();
    }
    
    @Override
    public String methodOfAppearance() {
        return super.methodOfAppearance();
    }

    public void prepareToyAndWish(){
        switch (name){
            case HANNA ->{
                toyForPrincess = Toys.TOY_PRINCESS;
                qualityForPrincess = Qualities.BEAUTY;
            }
            case JENNY ->{
                toyForPrincess = Toys.DOLL;
                qualityForPrincess = Qualities.COURAGE;
            }
            case MARY ->{
                toyForPrincess = Toys.BOOK_OF_FAIRY_TALES;
                qualityForPrincess = Qualities.DETERMINATION;
            }
            case KATRIN -> {
                toyForPrincess = Toys.BOX_OF_SWEETS;
                qualityForPrincess = Qualities.ENDURANCE;
            }
            case JUDY -> {
                toyForPrincess = Toys.DRESS;
                qualityForPrincess = Qualities.INTELLIGENCE;
            }
            case SHAIN ->{
                toyForPrincess = Toys.TEDDY_BEAR;
                qualityForPrincess = Qualities.KINDNESS;
            }
            case SIDNEY -> {
                toyForPrincess = Toys.TOY_CASTLE;
                qualityForPrincess = Qualities.POLITENESS;
            }
            default -> {
                toyForPrincess = Toys.HANDMADE_TOY;
                qualityForPrincess = Qualities.ANOTHER_QUALITY;
            }
        }
    }

    public void makeGift(){
        giftForPrincess = new Gift(toyForPrincess, qualityForPrincess);
    }

    public String giveGift(){
        return giftForPrincess + "\n" + toyForPrincess + " " + giftForPrincess.methodOfAppearance();
    }

    public void makeSetOfDishes(){
        switch (numberOfEnchantresses % 4){
            case 0 -> {
                plate = new Plate(Material.GOLDEN, PreciousStones.BRILLIANTS);
                fork = new Fork(Material.GOLDEN);
                spoon = new Spoon(Material.GOLDEN);
                knife = new Knife(Material.GOLDEN);
                glass = new Glass(Material.GOLDEN);
            }
            case 1 -> {
                plate = new Plate(Material.SILVER, PreciousStones.RUBIES);
                fork = new Fork(Material.SILVER);
                spoon = new Spoon(Material.SILVER);
                knife = new Knife(Material.SILVER);
                glass = new Glass(Material.SILVER);
            }
            case 2 -> {
                plate = new Plate(Material.PORCELAIN, PreciousStones.SAPPIRES);
                fork = new Fork(Material.PORCELAIN);
                spoon = new Spoon(Material.PORCELAIN);
                knife = new Knife(Material.PORCELAIN);
                glass = new Glass(Material.PORCELAIN);
            }
            case 3 -> {
                plate = new Plate(Material.CRYSTAL, PreciousStones.EMERALDS);
                fork = new Fork(Material.CRYSTAL);
                spoon = new Spoon(Material.CRYSTAL);
                knife = new Knife(Material.CRYSTAL);
                glass = new Glass(Material.CRYSTAL);
            }
            default -> {
                plate = new Plate(Material.ANOTHER_MATERIAL, PreciousStones.ANOTHER_STONE);
                fork = new Fork(Material.ANOTHER_MATERIAL);
                spoon = new Spoon(Material.ANOTHER_MATERIAL);
                knife = new Knife(Material.ANOTHER_MATERIAL);
                glass = new Glass(Material.ANOTHER_MATERIAL);
            }
        }
        setOfDishes = plate + ", " + fork + ", " + spoon + ", " + knife + ", " + glass;
    }

    public String getSetOfDishes(){
        return setOfDishes + " were in front of " + name;
    }

    public void makeDinner(){
        switch (numberOfEnchantresses){
            case 1 -> dinnerDish = DinnerDishes.PIGS_IN_BLANKETS;
            case 2 -> dinnerDish = DinnerDishes.COLD_ROAST_BEEF_AND_HORSERADISH_ROLLS;
            case 3 -> dinnerDish = DinnerDishes.CORONATION_CHICKEN;
            case 4 -> dinnerDish = DinnerDishes.ETON_MESS;
            case 5 -> dinnerDish = DinnerDishes.SALMON_AND_CUCUMBER_SANDWICHES;
            case 6 -> dinnerDish = DinnerDishes.STRAWBERRIES_AND_CREAM;
            case 7 -> dinnerDish = DinnerDishes.YORKSHIRE_PUDDING;
        }
    }

    public String getDinner(){
        return name + " is got " + dinnerDish;
    }

    @Override
    public String toString() {
        return "Enchantress " + name;
    }
}

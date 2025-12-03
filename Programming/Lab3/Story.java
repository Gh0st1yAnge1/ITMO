import Dishes.*;
import Heroes.*;
import Gifts.*;

public class Story {
    public static void main(String[] args) {


        Place place = new Place("in Kingdom", " at 12:30");
        System.out.println("Princess birthday happened " + place.place() + place.time());
        for (int i = 0; i < 7; i++){
            Enchantress e = new Enchantress(18, 56.6, 167.3);
            Plate plate = new Plate(Material.golden, PreciousStones.rubies);
            Fork fork = new Fork(Material.golden);
            Spoon spoon = new Spoon(Material.golden);
            Knife knife = new Knife(Material.golden);
            Glass glass = new Glass(Material.golden);
            e.getSetOfDishes(plate, fork, spoon, knife, glass);
            System.out.println(e);
        }
    }
}

package Gifts;

import Interface.*;

public record Gift(Gifts gift, Qualities quality) implements Appearance{
    @Override
    public void methodOfAppearance() {
        switch (gift){
            case teddy_bear, toy_castle, toy_princess -> System.out.print(" bumped from the sky on the ground.");
            case book_of_fairy_tales, box_of_sweets, doll -> System.out.print(" came out of the ground near.");
            case dress -> System.out.print(" wrapped the young lady's body.");
        };
    }

    @Override
    public String toString() {
        return gift + " and " + quality + " for young lady";
    }
}

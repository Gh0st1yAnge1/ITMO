package Gifts;

import Interface.*;

public record Gift(Toys toy, Qualities quality) implements Appearance{

    @Override
    public String methodOfAppearance() {
        switch (toy){
            case TEDDY_BEAR, TOY_CASTLE, TOY_PRINCESS -> {return "bumped from the sky on the ground.";}
            case BOOK_OF_FAIRY_TALES, BOX_OF_SWEETS, DOLL -> {return "came out of the ground near.";}
            case DRESS -> {return "wrapped the young lady's body.";}
            default -> {return "something appears";}
        }
    }

    @Override
    public String toString() {
        return toy + " and " + quality + " for young lady";
    }
}

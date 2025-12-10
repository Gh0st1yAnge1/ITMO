package Interfaces;

import Gifts.Gift;

public interface RecieveGift {
    default void recieveGift(Gift gift){
        System.out.println(" got " + gift);
    }
}

package Interfaces;

import Gifts.*;

public interface MakeGift {
    default Gift makeGift(Toys toy, Qualities qualitiy){
        return new Gift(toy, qualitiy);
    }
}

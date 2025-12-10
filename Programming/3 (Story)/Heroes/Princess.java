package Heroes;

import Gifts.Gift;
import Interfaces.RecieveGift;

public class Princess extends Hero implements RecieveGift {

    public Princess(String name){
        super(name);
        this.name = name;
    }

    @Override
    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    @Override
    public void showEmotion() {
        super.showEmotion();
    }

    @Override
    public void recieveGift(Gift gift) {
        System.out.print(name); RecieveGift.super.recieveGift(gift);
    }

    @Override
    public String toString() {
        return "Princess " + name;
    }
}

package heroes;

import exceptions.GiftOverflow;
import gifts.Gift;
import interfaces.Reciever;

public class Princess extends Hero implements Reciever {

    private String name;
    private Emotion emotion;


    public Princess(String name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
    }

    public void showGifts(){
        System.out.println(christineGifts);
    }

    @Override
    public String toString() {
        return "Princess " + name + " feels " + emotion;
    }

    @Override
    public Gift recieveGift(Gift gift) throws GiftOverflow {
        if (christineGifts.size() == 4){
            throw new GiftOverflow();
        } else{
            christineGifts.add(gift);
            System.out.println(name + " got " + gift);
        }
        return gift;
    }
}

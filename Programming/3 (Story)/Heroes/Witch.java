package Heroes;

import Interface.Appearance;

public class Witch extends Hero implements Appearance {

    public Witch(Names name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
    }

    @Override
    public void methodOfAppearance() {
        System.out.println("Witch " + name + " flew up inside on the broom.");
    }

    @Override
    public void showEmotion() {
        super.showEmotion();
    }

    @Override
    public String toString() {
        return "Witch " + name;
    }
}

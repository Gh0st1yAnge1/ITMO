package Heroes;

import Interface.Appearance;

public class Witch extends Hero implements Appearance {

    public Witch(Names name){
        super(name);
        this.name = name;
    }

    @Override
    public String methodOfAppearance() {
        return super.methodOfAppearance();
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
    public String toString() {
        return "Witch " + name;
    }
}

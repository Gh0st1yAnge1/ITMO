package Heroes;

import Interface.Appearance;

public class Hero implements Appearance {
    protected Names name;
    protected Emotion emotion;

    public Hero(Names name){
        this.name = name;
    }

    @Override
    public String methodOfAppearance() {
        switch (name){
            case DELAYLA -> {return name + " flew up inside on the broom";}
            case SIDNEY, HANNA, SHAIN, JUDY, JENNY, MARY, KATRIN -> {return name + " appeared with clap";}
            default -> {return name + " came";}
        }
    }

    public void setEmotion(){
        switch (name){
            case ALICE -> emotion = Emotion.AMAZED;
            case JUDY ->  emotion = Emotion.ASTONISHED;
            case MARY -> emotion = Emotion.AMUSED;
            case HANNA -> emotion = Emotion.ECSTATIC;
            case JENNY -> emotion = Emotion.DELIGHTED;
            case SHAIN -> emotion = Emotion.OVERJOYED;
            case KATRIN ->  emotion = Emotion.SHOCKED;
            case SIDNEY -> emotion = Emotion.THRILLED;
            case DELAYLA -> emotion = Emotion.STUNNED;
        }
    }

    public String showEmotion(){
        return name + " was " + emotion;
    }

    @Override
    public String toString() {
        return "Hero" + name;
    }
}

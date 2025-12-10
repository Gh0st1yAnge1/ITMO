package Heroes;

public class Witch extends Hero {

    public Witch(String  name){
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
    public String toString() {
        return "Witch " + name;
    }
}

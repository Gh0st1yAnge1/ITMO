package Heroes;

public class Princess extends Hero{

    public Princess(Names name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
    }

    @Override
    public void showEmotion() {
        super.showEmotion();
    }

    @Override
    public String toString() {
        return "Princess " + name;
    }
}

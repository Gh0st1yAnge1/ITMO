package heroes;

public class Witch extends Hero {

    private String name;
    private Emotion emotion;

    public Witch(String name, Emotion emotion){
        super(name, emotion);
        this.name = name;
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return "Witch " + name + " feels " + emotion;
    }
}

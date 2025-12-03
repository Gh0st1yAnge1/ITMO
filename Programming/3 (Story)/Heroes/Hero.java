package Heroes;

public class Hero{
    protected Names name;
    protected Emotion emotion;

    public Hero(Names name, Emotion emotion){
        this.name = name;
        this.emotion = emotion;
    }

    public void showEmotion(){
        System.out.print(emotion);
    }

    @Override
    public String toString() {
        return "Hero" + name;
    }
}

package Heroes;

public abstract class Hero{
    protected String name;
    protected Emotion emotion;

    public Hero(String name){
        this.name = name;
    }

    public abstract void setEmotion(Emotion emotion);

    public void showEmotion(){
        System.out.println(name + " was " + emotion);
    }

    @Override
    public String toString() {
        return "Hero" + name;
    }
}

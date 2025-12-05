package Heroes;

public class Princess extends Hero{

    public Princess(Names name){
        super(name);
        this.name = name;
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
        return "Princess " + name;
    }
}

package Heroes;

public class Witch extends Hero{

    public Witch(int age, double weight, double height, String name){
        super(age, weight, height, name);
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.name = name;
    }

    @Override
    public String toString() {
        return "witch";
    }
}

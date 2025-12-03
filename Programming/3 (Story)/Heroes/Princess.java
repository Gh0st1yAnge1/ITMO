package Heroes;

public class Princess extends Hero{

    public Princess(int age, double weight, double height, String name){
        super(age, weight, height, name);
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.name = name;
    }

    @Override
    public String toString() {
        return "princess " + name;
    }
}

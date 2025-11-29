package Dishes;

public class Spoon extends Dishes{
    protected Material material;

    public Spoon(Material material){
        this.material = material;
    }

    @Override
    public String toString() {
        return material + " spoon";
    }
}
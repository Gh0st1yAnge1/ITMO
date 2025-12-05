package Dishes;

public class Fork extends Dishes{
    protected Material material;

    public Fork(Material material){
        this.material = material;
    }

    @Override
    public String toString() {
        return material + " fork";
    }
}
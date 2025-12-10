package Dishes;

public record SetOfDishes(Plate plate, Fork fork, Spoon spoon, Knife knife, Glass glass) {

    @Override
    public String toString() {
        return plate + ", " + fork + ", " + spoon + ", " + knife + ", " + glass;
    }
}

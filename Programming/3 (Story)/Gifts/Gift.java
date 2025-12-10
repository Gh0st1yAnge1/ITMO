package Gifts;

public record Gift(Toys toy, Qualities quality){

    @Override
    public String toString() {
        return toy + " and " + quality;
    }
}

package Dishes;

import Interface.Appearance;

public enum DinnerDishes implements Appearance {
    CORONATION_CHICKEN, ETON_MESS, SALMON_AND_CUCUMBER_SANDWICHES,
    COLD_ROAST_BEEF_AND_HORSERADISH_ROLLS, STRAWBERRIES_AND_CREAM,
    YORKSHIRE_PUDDING, PIGS_IN_BLANKETS;

    @Override
    public String methodOfAppearance() {
        switch (this){
            case ETON_MESS, CORONATION_CHICKEN, YORKSHIRE_PUDDING -> {return " which was delivered by ghosts";}                                                // функция определяет
            case COLD_ROAST_BEEF_AND_HORSERADISH_ROLLS, SALMON_AND_CUCUMBER_SANDWICHES, STRAWBERRIES_AND_CREAM -> {return " which have flown from kitchen";}   // каким образом появилось блюдо на столе
            case PIGS_IN_BLANKETS -> {return " which ran from the yard";}
            default -> {return " which appeared";}
        }
    }

    @Override
    public String toString() {
        return name() + methodOfAppearance();  //можно написать this.name() и this.methodOfAppearance(), но избыточно
    }
}

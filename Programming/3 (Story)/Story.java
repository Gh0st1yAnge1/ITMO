import Dishes.*;
import Heroes.*;
import Gifts.*;

public class Story {
    public static void main(String[] args) {

        StoryText text = new StoryText();
        System.out.println(text);
        text.preparing();
        text.part1();
    }
}

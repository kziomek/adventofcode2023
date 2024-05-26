package advent.day2;

import java.util.Map;
import java.util.Set;

public class Game {

    public static final String RED = "red";
    public static final String GREEN = "green";
    private static final String BLUE = "blue";

    Integer id;

    Set<Map<String, Integer>> setsOfHands;

    public Game(Integer id, Set<Map<String, Integer>> setsOfHands) {
        this.id = id;
        this.setsOfHands = setsOfHands;
    }
}

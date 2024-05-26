package advent.day2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CubeConundrum {

    public static void main(String[] args) throws IOException {

//        List<Game> games = Parser.parse(Paths.get("src/main/resources/day2/example-part1.txt"));
        List<Game> games = Parser.parse(Paths.get("src/main/resources/day2/my-input.txt"));

        System.out.println(runPart1(games));
    }

    private static Integer runPart1(List<Game> games){
        return games.stream()
            .filter(filterGamePredicate())
            .mapToInt(game -> game.id)
            .sum();
    }

    private static Predicate<Game> filterGamePredicate() {
        return game -> {
            for (Map<String, Integer> setOfHands : game.setsOfHands) {
                if (setOfHands.containsKey("red") && setOfHands.get("red") > 12) {
                    return false;
                }
                if (setOfHands.containsKey("green") && setOfHands.get("green") > 13) {
                    return false;
                }
                if (setOfHands.containsKey("blue") && setOfHands.get("blue") > 14) {
                    return false;
                }
            }
            return true;
        };
    }

}

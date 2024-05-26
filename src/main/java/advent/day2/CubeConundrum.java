package advent.day2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class CubeConundrum {

    public static void main(String[] args) throws IOException {

//        List<Game> games = Parser.parse(Paths.get("src/main/resources/day2/example-part1.txt"));
                List<Game> games = Parser.parse(Paths.get("src/main/resources/day2/my-input.txt"));

        //        System.out.println(runPart1(games));
        System.out.println(runPart2(games));
    }

    private static Integer runPart2(List<Game> games) {
        Set<Map<String, Integer>> gameMinCubeCounts = collectMinimumRequiredCubeNumbers(games);
        return calculateSumOfPowers(gameMinCubeCounts);
    }

    private static int calculateSumOfPowers(Set<Map<String, Integer>> gameMinCubeCounts) {
        return gameMinCubeCounts.stream()
            .map(gameMinCubeCount -> gameMinCubeCount
                .values()
                .stream()
                .reduce(1, (a, b) -> a * b))
            .mapToInt(i -> i)
            .sum();
    }

    private static Set<Map<String, Integer>> collectMinimumRequiredCubeNumbers(List<Game> games) {
        Set<Map<String, Integer>> gameMinCubeCounts = new HashSet<>();
        for (Game game : games) {
            Map<String, Integer> gameMap = new HashMap<>();
            for (Map<String, Integer> setOfHand : game.setsOfHands) {
                if (setOfHand.containsKey("red")) {
                    gameMap.merge("red", setOfHand.get("red"), (v1, v2) -> v2 > v1 ? v2 : v1);
                }
                if (setOfHand.containsKey("green")) {
                    gameMap.merge("green", setOfHand.get("green"), (v1, v2) -> v2 > v1 ? v2 : v1);
                }
                if (setOfHand.containsKey("blue")) {
                    gameMap.merge("blue", setOfHand.get("blue"), (v1, v2) -> v2 > v1 ? v2 : v1);
                }
            }
            gameMinCubeCounts.add(gameMap);
        }
        return gameMinCubeCounts;
    }

    private static Integer runPart1(List<Game> games) {
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

package advent.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {

    public static List<Game> parse(Path input) throws IOException {
        return Files.readAllLines(input)
            .stream()
            .map(line -> {
                String[] lineSplitByColon = line.split(":");

                Integer id = Integer.valueOf(lineSplitByColon[0].split(" ")[1]);
                Set<Map<String, Integer>> hands = parseHands(lineSplitByColon[1]);

                return new Game(id, hands);
            }).toList();
    }

    private static Set<Map<String, Integer>> parseHands(String handsStr) {
        Set<Map<String, Integer>> hands = new HashSet<>();

        String[] splitBySemicolon = handsStr.split(";");
        for (String handStr : splitBySemicolon) {
            hands.add(parseHand(handStr));
        }

        return hands;
    }

    private static Map<String, Integer> parseHand(String handStr) {
        Map<String, Integer> cubesByColour = new HashMap<>();
        String[] splitByComa = handStr.split(",");
        for (String cubeByColourStr : splitByComa) {
            String[] split = cubeByColourStr.trim().split(" ");
            cubesByColour.put(split[1], Integer.valueOf(split[0]));
        }
        return cubesByColour;
    }
}

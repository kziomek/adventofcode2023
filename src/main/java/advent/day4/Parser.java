package advent.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {

    public static List<Card> parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream()
            .map(line -> {
                String[] cardName = line.split(":")[0].split(" ");
                int id = Integer.parseInt(cardName[cardName.length - 1]);

                String[] numberLists = line.split(":")[1].split("\\|");
                return new Card(id, parseNumbers(numberLists[0]), parseNumbers(numberLists[1]));
            }).toList();
    }

    private static Set<Integer> parseNumbers(String numberList) {
        return Arrays.stream(numberList.trim().split(" "))
            .filter(s -> !s.isEmpty())
            .map(Integer::valueOf)
            .collect(Collectors.toSet());
    }
}

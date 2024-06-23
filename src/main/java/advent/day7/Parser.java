package advent.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static List<Hand> parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream()
            .map(line -> line.split(" "))
            .map(hand -> new Hand(hand[0], Long.valueOf(hand[1])))
            .collect(Collectors.toList());
    }
}

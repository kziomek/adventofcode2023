package advent.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Parser {
    public static List<Brick> parse(Path path) throws IOException {
        return Files.readAllLines(path).stream()
            .map(line -> new Brick(line.split("~")))
            .toList();
    }
}

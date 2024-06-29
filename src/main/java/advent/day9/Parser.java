package advent.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static List<List<Long>> parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream()
            .map(line -> Arrays.stream(line.split(" "))
                .map(Long::valueOf)
                .toList())
            .toList();
    }
}

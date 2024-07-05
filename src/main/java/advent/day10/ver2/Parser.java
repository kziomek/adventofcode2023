package advent.day10.ver2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {

    public static char[][] parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }
}

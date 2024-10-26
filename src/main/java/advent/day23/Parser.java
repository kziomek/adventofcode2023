package advent.day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {

    public static char[][] parse(Path path) throws IOException {
        return Files.readAllLines(path)
            .stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }
}

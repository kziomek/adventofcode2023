package advent.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {

    public static char[][] parse(String path) throws IOException {
        return Files.readAllLines(Paths.get(path))
            .stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }
}

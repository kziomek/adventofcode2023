package advent.day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Parser {

    public static List<Input> parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path)).stream()
            .map(line -> line
                .replace('@', ',')
                .replace(" ", "")
                .split(","))
            .map(Input::new)
            .toList();
    }
}

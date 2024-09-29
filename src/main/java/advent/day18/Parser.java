package advent.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public static List<Step> parse(String path) throws IOException {
        return Files.readAllLines(Path.of(path)).stream()
            .map(line -> line.split(" "))
            .map(l -> new Step(l[0].toCharArray()[0], l[1], l[2]))
            .collect(Collectors.toList());
    }
}

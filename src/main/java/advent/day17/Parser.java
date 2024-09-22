package advent.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser {

    public static CityBlock[][] parse(String path) throws IOException {
        int[][] array = Files.readAllLines(Path.of(path)).stream()
            .map(s -> s.chars().map(c -> Character.digit(c, 10)))
            .map(IntStream::toArray).toArray(int[][]::new);

        CityBlock[][] blocks = new CityBlock[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                blocks[i][j] = new CityBlock(i, j, array[i][j]);
            }
        }
        return blocks;
    }
}

package advent.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static List<RecordRace> parse(String path) throws IOException {
        List<RecordRace> recordRaces = new ArrayList<>();
        List<List<Integer>> lists = Files.readAllLines(Path.of(path))
            .stream()
            .map(line -> Arrays.stream(line.split(":")[1]
                    .split(" ")).filter(e -> !e.isEmpty())
                .map(Integer::valueOf)
                .toList()
            )
            .toList();

        int games = lists.get(0).size();
        for (int i = 0; i < games; i++) {
            recordRaces.add(new RecordRace(lists.get(0).get(i), lists.get(1).get(i)));
        }
        return recordRaces;
    }
}

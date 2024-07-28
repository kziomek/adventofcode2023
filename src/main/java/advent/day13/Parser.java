package advent.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    public static List<char[][]> parse(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        Iterator<String> iterator = lines.iterator();
        List<List<String>> grids = new ArrayList<>();
        List<String> grid = new ArrayList<>();
        do {
            String line = iterator.next();
            System.out.println(line);
            if (line.isEmpty()) {
                grids.add(grid);
                grid = new ArrayList<>();
            } else {
                grid.add(line);
            }
        } while (iterator.hasNext());
        grids.add(grid);

        return convert(grids);
    }

    private static List<char[][]> convert(List<List<String>> grids) {
        List<char[][]> arrs = new ArrayList<>();
        for (List<String> grid : grids) {
            char[][] arr = new char[grid.size()][grid.get(0).length()];

            for (int i = 0; i < grid.size(); i++) {
                for (int j = 0; j < grid.get(0).length(); j++) {
                    arr[i][j] = grid.get(i).charAt(j);
                }
            }
            arrs.add(arr);
        }
        return arrs;
    }
}

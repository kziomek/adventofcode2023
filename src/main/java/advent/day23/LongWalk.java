package advent.day23;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LongWalk {
    public static void main(String[] args) throws IOException {
//        char[][] grid = Parser.parse(Path.of("src/main/resources/day23/example.txt"));
        char[][] grid = Parser.parse(Path.of("src/main/resources/day23/my-input.txt"));

        printGrid(grid);

        Set<String> seen = new HashSet<>();
        //        seen.add(key(0, 1));
        List<Integer> walkLengths = new ArrayList<>();
        findLongestPath(grid, 0, 1, seen, walkLengths);
        walkLengths.sort(Comparator.comparingInt(i -> i));
        System.out.println(walkLengths);
        System.out.println("Max walk is " + walkLengths.getLast());
    }

    private static String key(int i, int j) {
        return "" + i + "_" + j;
    }

    private static void findLongestPath(char[][] grid, int i, int j, Set<String> seen, List<Integer> walkLengths) {
        if (seen.contains(key(i, j))) {
            //been here before
            return;
        }
        seen.add(key(i, j));
        if (i == grid.length - 1) {
            walkLengths.add((seen.size() - 1));
            System.out.println("Walk length " + (seen.size() - 1)); // deduct 1 as start position
            // the end
            //            printGrid(grid, seen);
            return;
        }

        int possibleDirections = countPossibleDirections(grid, i, j, seen);

        // here try walking in any direction
        if (possibleDirections == 0) { //dead end
            return;
        }

        if (possibleDirections != 1) { // todo we have to clone seen set
            //            System.out.println("possible directions " + possibleDirections + " at [" + i + "][" + j + "]");
        }

        if (canGo(grid, i - 1, j, seen) && (grid[i][j] == '.' || grid[i][j] == '^')) {
            findLongestPath(grid, i - 1, j, new HashSet<>(seen), walkLengths);
        }
        if (canGo(grid, i + 1, j, seen) && (grid[i][j] == '.' || grid[i][j] == 'v')) {
            findLongestPath(grid, i + 1, j, new HashSet<>(seen), walkLengths);
        }
        if (canGo(grid, i, j - 1, seen) && (grid[i][j] == '.' || grid[i][j] == '<')) {
            findLongestPath(grid, i, j - 1, new HashSet<>(seen), walkLengths);
        }
        if (canGo(grid, i, j + 1, seen) && (grid[i][j] == '.' || grid[i][j] == '>')) {
            findLongestPath(grid, i, j + 1, new HashSet<>(seen), walkLengths);
        }
        return;
    }

    private static int countPossibleDirections(char[][] grid, int i, int j, Set<String> seen) {
        int count = 0;
        if (canGo(grid, i - 1, j, seen)) {
            count++;
        }
        if (canGo(grid, i + 1, j, seen)) {
            count++;
        }
        if (canGo(grid, i, j - 1, seen)) {
            count++;
        }
        if (canGo(grid, i, j + 1, seen)) {
            count++;
        }
        return count;
    }

    private static boolean canGo(char[][] grid, int i, int j, Set<String> seen) {
        if (i >= 0 && i <= grid.length - 1 && j > -0 && j <= grid[0].length && grid[i][j] != '#' && !seen.contains(key(i, j))) {
            return true;
        }
        return false;
    }

    private static void printGrid(char[][] grid, Set<String> seen) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (seen.contains(key(i, j))) {
                    System.out.print('O');
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void printGrid(char[][] grid) {
        for (char[] chars : grid) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
}

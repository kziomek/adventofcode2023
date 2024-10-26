package advent.day23;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO stack overflow -> refactor solution to use queue or optimize sets
public class LongWalk {
    public static void main(String[] args) throws IOException {
        char[][] grid = Parser.parse(Path.of("src/main/resources/day23/example.txt"));
//                char[][] grid = Parser.parse(Path.of("src/main/resources/day23/my-input.txt"));

        printGrid(grid);

        int maxWalkLength = findLongestPath(grid);

        System.out.println("Max walk is " + maxWalkLength);
    }

    private static String key(int i, int j) {
        return "" + i + "_" + j;
    }

    private static int findLongestPath(char[][] grid) {
//        Set<String> seen = new HashSet<>();
        //        seen.add(key(0, 1));
        List<Integer> walkLengths = new ArrayList<>();

        Deque<StackElement> stack = new LinkedList<>();
        stack.add(new StackElement(0, 1, new HashSet<>())); // start position

        while (!stack.isEmpty()) {
            StackElement walk = stack.pollLast();
            System.out.println(walk);
            if (walk.seen.contains(key(walk.i, walk.j))) {
                System.out.println("Been here before");
                //been here before
                continue;
            }
            walk.seen.add(key(walk.i, walk.j));
            if (walk.i == grid.length - 1) {
                walkLengths.add((walk.seen.size() - 1));
                System.out.println("Walk length " + (walk.seen.size() - 1)); // deduct 1 as start position
                // the end
                //            printGrid(grid, seen);
                continue;
            }
            int possibleDirections = countPossibleDirections(grid, walk.i, walk.j, walk.seen);

            // here try walking in any direction
            if (possibleDirections == 0) { //dead end
                continue;
            }
            if (possibleDirections != 1) { // todo we have to clone seen set
                            System.out.println("possible directions " + possibleDirections + " at [" + walk.i + "][" + walk.j + "]");
            }

            if (canGo(grid, walk.i - 1, walk.j, walk.seen) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '^')) {
                //            if (canGo(grid, walk.i - 1, walk.j, seen)) {
                stack.add(new StackElement(walk.i - 1, walk.j, new HashSet<>(walk.seen)));
            }
            if (canGo(grid, walk.i + 1, walk.j, walk.seen) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == 'v')) {
                //            if (canGo(grid, walk.i + 1, walk.j, seen)) {
                stack.add(new StackElement(walk.i + 1, walk.j, new HashSet<>(walk.seen)));
            }
            if (canGo(grid, walk.i, walk.j - 1, walk.seen) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '<')) {
                //            if (canGo(grid, walk.i, walk.j - 1, seen)) {
                stack.add(new StackElement(walk.i, walk.j - 1, new HashSet<>(walk.seen)));
            }
            if (canGo(grid, walk.i, walk.j + 1, walk.seen) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '>')) {
                //            if (canGo(grid, walk.i, walk.j + 1, seen)) {
                stack.add(new StackElement(walk.i, walk.j + 1, new HashSet<>(walk.seen)));
            }
        }

        walkLengths.sort(Comparator.comparingInt(i -> i));
        System.out.println(walkLengths);
        return walkLengths.getLast();
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

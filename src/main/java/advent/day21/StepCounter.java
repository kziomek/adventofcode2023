package advent.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StepCounter {

    /**
     * In this solution we need to observe that from certain number of steps there is repeatable pattern which can be used to calculate further number of steps.
     * In my example pattern length is 131 steps.
     * In consecutive pattern elements growing steadily by fixed number of steps.
     * patternIters - number of pattern lenghts needed to reach defined step.
     * patternIters is multiplied by sum of last diffs in pattern length - this is how much number of steps would grow if not expanded
     * therefore we need to add how quickly sum of diffs is growing in next pattern.
     *
     *
     * // total number of 'O' = lastCounter + patternIters * sum(lastDiffs) + multiplier * sum(diffDelta)
     */
    public static void main(String[] args) throws IOException {
        // my-input config
        int stepsToBuildPattern = 1024;
        int steps = 26501365;

        // example config
        //        int stepsToBuildPattern = 116;
        //        int steps = 5000;

        char[][] grid = Files.readAllLines(Path.of("src/main/resources/day21/my-input.txt"))
            //        char[][] grid = Files.readAllLines(Path.of("src/main/resources/day21/example.txt"))
            .stream()
            .map(String::toCharArray).toArray(char[][]::new);

        grid = enlargeGrid(grid, 31);

        //        print(grid);

        List<Integer> counts = new ArrayList<>();

        List<Integer> diffs = new ArrayList<>();
        int patternLength = -1;
        for (int i = 0; i < stepsToBuildPattern; i++) {
            System.out.println();
            grid = iterate(grid);
            int count = countReachedFields(grid);
            counts.add(count);
            System.out.println(i + " " + count);
            if (i > 0) {
                diffs.add(counts.get(i) - counts.get(i - 1));
            }

            patternLength = findPatternLength(diffs);
            if (patternLength > 0) {
                boolean isValid = validatePattern(diffs, patternLength);
                if (isValid) {
                    int rest = (steps - diffs.size() - 1) % patternLength;
                    if (rest == 0) {
                        break;
                    }
                }
            }

            System.out.println("Pattern length " + patternLength);
        }

        validateGrid(grid);

        System.out.println("Result " + counts);

        System.out.println("diifs " + diffs);

        int patternIters = (steps - (counts.size())) / patternLength;

        long[] diffDelta = new long[patternLength];
        for (int i = 0; i < diffDelta.length; i++) {
            diffDelta[i] = diffs.get(diffs.size() - patternLength + i) - diffs.get(diffs.size() - 2 * patternLength + i);
        }

        long[] lastCounts = new long[patternLength];
        for (int i = 0; i < lastCounts.length; i++) {
            lastCounts[i] = counts.get(counts.size() - patternLength + i);
        }

        long[] lastDiffs = new long[patternLength];
        for (int i = 0; i < lastDiffs.length; i++) {
            lastDiffs[i] = diffs.get(diffs.size() - patternLength + i);
        }

        System.out.println("patternIters " + patternIters);

        long multiplier = multiplier(patternIters);

        System.out.println("multiplier " + multiplier);

        print("lastCounts", lastCounts);
        print("lastDiffs", lastDiffs);
        print("diffDelta", diffDelta);

        long result = calculateRes(lastCounts[lastCounts.length - 1], lastDiffs, diffDelta, patternIters, multiplier);

        System.out.println("Result " + result);
    }

    private static boolean validatePattern(List<Integer> diffs, int patternLength) {
        for (int i = 0; i < patternLength; i++) {
            int id1 = diffs.size() - 1 - i;
            int id2 = diffs.size() - 1 - i - patternLength;
            int id3 = diffs.size() - 1 - i - 2 * patternLength;
            //            System.out.println(" " + diffs.get(id1) + " " + diffs.get(id2) + " " + diffs.get(id3));
            if (diffs.get(id1) - diffs.get(id2) != diffs.get(id2) - diffs.get(id3)) {
                return false;
            }
        }
        return true;
    }

    private static int findPatternLength(List<Integer> diffs) {
        int lastIndex = diffs.size() - 1;
        for (int i = 1; i < diffs.size() / 3; i++) {
            if (diffs.get(lastIndex) - diffs.get(lastIndex - i) == diffs.get(lastIndex - i) - diffs.get(lastIndex - 2 * i) && diffs.get(lastIndex - i) - diffs.get(lastIndex - 2 * i) == diffs.get(lastIndex - 2 * i) - diffs.get(lastIndex - 3 * i)) {
                System.out.println("Pattern distance " + i);
                System.out.println(" " + diffs.get(lastIndex - 2 * i) + " " + diffs.get(lastIndex - i) + " " + diffs.get(lastIndex));
                return i;
            }
        }
        return -1;
    }

    //lastCounter + iters* sum(lastDiffs) + multiplier * sum(diffDelta)
    private static long calculateRes(long lastCount, long[] lastDiffs, long[] diffDelta, long iters, long multiplier) {
        return lastCount + iters * sum(lastDiffs) + multiplier * sum(diffDelta);
    }

    private static long sum(long[] arr) {
        long sum = 0;
        for (long l : arr) {
            sum += l;
        }
        return sum;
    }

    private static long multiplier(long iters) {
        long result;
        if (iters % 2 == 0) {
            result = (1 + iters) * (iters / 2);
        } else {
            result = (1 + iters) * (iters / 2) + (iters / 2 + 1);
        }
        return result;
    }

    private static void validateGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 'O' || grid[i][grid[0].length - 1] == 'O') {
                System.out.println("Too small grid");
                System.exit(-1);
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[0][j] == 'O' || grid[grid.length - 1][j] == 'O') {
                System.out.println("Too small grid");
                System.exit(-1);
            }
        }
    }

    private static char[][] enlargeGrid(char[][] grid, int times) {
        char[][] enlargedGrid = new char[times * grid.length][times * grid[0].length];
        for (int i = 0; i < enlargedGrid.length; i++) {
            for (int j = 0; j < enlargedGrid[0].length; j++) {
                enlargedGrid[i][j] = grid[i % grid.length][j % grid[0].length];
                if ((i != enlargedGrid.length / 2 || j != enlargedGrid[0].length / 2) && enlargedGrid[i][j] == 'S') {
                    enlargedGrid[i][j] = '.';
                }
            }
        }

        return enlargedGrid;
    }

    private static int countReachedFields(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char c : row) {
                if (c == 'O') {
                    count++;
                }
            }
        }

        return count;
    }

    private static char[][] iterate(char[][] grid) {
        char[][] updatedGrid = copyGarden(grid);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'O' || grid[i][j] == 'S') {
                    addSteps(updatedGrid, i, j);
                }
            }
        }
        return updatedGrid;
    }

    private static void addSteps(char[][] iteratedGrid, int i, int j) {
        attemptAddingSteps(iteratedGrid, i - 1, j);
        attemptAddingSteps(iteratedGrid, i + 1, j);
        attemptAddingSteps(iteratedGrid, i, j - 1);
        attemptAddingSteps(iteratedGrid, i, j + 1);
    }

    private static void attemptAddingSteps(char[][] iteratedGrid, int i, int j) {
        if (i < 0 || j < 0 || i >= iteratedGrid.length || j >= iteratedGrid[0].length) {
            return;
        }
        if (iteratedGrid[i][j] == '#') {
            return;
        }
        iteratedGrid[i][j] = 'O';
    }

    private static char[][] copyGarden(char[][] grid) {
        char[][] newGrid = new char[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = copyField(grid[i][j]);
            }
        }
        return newGrid;
    }

    private static char copyField(char c) {
        return c == '#' ? '#' : '.';
    }

    private static void print(char[][] grid) {
        for (char[] line : grid) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static void print(String label, long[] arr) {
        System.out.print(label + "  ");
        for (long c : arr) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}

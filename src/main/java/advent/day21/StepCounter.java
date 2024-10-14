package advent.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StepCounter {

    public static void main(String[] args) throws IOException {
        char[][] grid = Files.readAllLines(Path.of("src/main/resources/day21/my-input.txt"))
            .stream()
            .map(String::toCharArray).toArray(char[][]::new);

        print(grid);

        for (int i = 0; i < 64; i++) {
            System.out.println();
            grid = iterate(grid);
            print(grid);
        }

        int count = countReachedFields(grid);
        System.out.println("Result " + count);
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
}

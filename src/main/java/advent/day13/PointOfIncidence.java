package advent.day13;

import java.io.IOException;
import java.util.List;

public class PointOfIncidence {

    public static void main(String[] args) throws IOException {

        //        List<char[][]> grids = Parser.parse("src/main/resources/day13/example.txt");
        List<char[][]> grids = Parser.parse("src/main/resources/day13/my-input.txt");

        int result = calculate(grids);

        System.out.println(result);
    }

    private static int calculate(List<char[][]> grids) {
        int total = 0;
        for (char[][] grid : grids) {
            total += calculate(grid);
        }
        return total;
    }

    private static int calculate(char[][] grid) {
        int col = calculateColumn(grid);
        int row = calculateRow(grid);

        return 100 * row + col;
    }

    private static int calculateColumn(char[][] grid) {
        for (int i = grid[0].length - 2; i >= 0; i--) {
            if (reflectsVertically(grid, i)) {
                return i + 1;
            }
        }
        return 0;
    }

    private static boolean reflectsVertically(char[][] grid, int col) {
        int l = col;
        int r = col + 1;

        while (l >= 0 && r < grid[0].length) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][l] != grid[j][r]) {
                    return false;
                }
            }
            l--;
            r++;
        }
        return true;
    }

    private static int calculateRow(char[][] grid) {
        for (int i = grid.length - 2; i >= 0; i--) {
            if (reflectsHorizontaly(grid, i)) {
                return i + 1;
            }
        }
        return 0;
    }

    private static boolean reflectsHorizontaly(char[][] grid, int row) {
        int top = row;
        int bottom = row + 1;

        while (top >= 0 && bottom < grid.length) {

            for (int i = 0; i < grid[0].length; i++) {
                if (grid[top][i] != grid[bottom][i]) {
                    return false;
                }
            }
            top--;
            bottom++;
        }
        return true;
    }
}

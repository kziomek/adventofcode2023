package advent.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GearRatios {

    public static void main(String[] args) throws IOException {
//        char[][] arr = loadData("src/main/resources/day3/example-part1.txt");
        char[][] arr = loadData("src/main/resources/day3/my-input.txt");

        runPart1(arr);
    }

    private static void runPart1(char[][] arr) {
        int result = 0;
        for (int row = 0; row < arr.length; row++) {
            int num = 0;
            boolean isAdjacentToSymbol = false;
            for (int col = 0; col < arr[0].length; col++) {
                char c = arr[row][col];
                if (Character.isDigit(c)) {
                    num = num * 10 + Character.digit(c, 10);
                    if (!isAdjacentToSymbol) {
                        isAdjacentToSymbol = hasNeighbouringSymbol(arr, row, col);
                    }
                }
                //if next is not digit then add collected number and reset
                if (col + 1 == arr[0].length || !Character.isDigit(arr[row][col + 1])) {
                    if (isAdjacentToSymbol) {
                        result += num;
                    }
                    num = 0;
                    isAdjacentToSymbol = false;
                }
            }
        }
        System.out.println(result);
    }

    private static char[][] loadData(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream().map(String::toCharArray)
            .toArray(char[][]::new);
    }

    private static boolean hasNeighbouringSymbol(char[][] arr, int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i >= arr.length) {
                    continue;
                }
                if (j < 0 || j >= arr[0].length) {
                    continue;
                }
                char c = arr[i][j];
                if (isSymbol(c)) {
                    return true; // it means it's symbol
                }
            }
        }

        return false;
    }

    private static boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }
}

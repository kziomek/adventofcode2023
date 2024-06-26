package advent.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GearRatios {

    public static void main(String[] args) throws IOException {
        //        char[][] arr = loadData("src/main/resources/day3/example-part1.txt");
        char[][] arr = loadData("src/main/resources/day3/my-input.txt");

        System.out.println(runPart1(arr));
        System.out.println(runPart2(arr));
    }

    /*
        In first version algorithm assumed there is only one asterisk adjacent to a number and it was sufficient for given input
     */
    private static int runPart2(char[][] arr) {
        Map<String, List<Integer>> numbers = new HashMap<>();
        for (int row = 0; row < arr.length; row++) {
            int num = 0;
            Optional<String> adjacentAsteriskKey = Optional.empty();
            for (int col = 0; col < arr[0].length; col++) {
                char c = arr[row][col];
                if (Character.isDigit(c)) {
                    num = num * 10 + Character.digit(c, 10);
                    if (adjacentAsteriskKey.isEmpty()) {
                        adjacentAsteriskKey = getFirstNeighbouringSymbolPosition(arr, row, col, GearRatios::isAsteriskSymbol);
                    }
                }
                //if next is not digit then add collected number and reset
                if (col + 1 == arr[0].length || !Character.isDigit(arr[row][col + 1])) {
                    if (adjacentAsteriskKey.isPresent()) {
                        numbers.computeIfAbsent(adjacentAsteriskKey.get(), k -> new LinkedList<>()).add(num);
                    }
                    num = 0;
                    adjacentAsteriskKey = Optional.empty();
                }
            }
        }

        return numbers.values()
            .stream()
            .filter(list -> list.size() == 2)
            .map(list -> list.getFirst() * list.getLast())
            .mapToInt(i -> i)
            .sum();
    }

    private static int runPart1(char[][] arr) {
        int result = 0;
        for (int row = 0; row < arr.length; row++) {
            int num = 0;
            boolean isAdjacentToSymbol = false;
            for (int col = 0; col < arr[0].length; col++) {
                char c = arr[row][col];
                if (Character.isDigit(c)) {
                    num = num * 10 + Character.digit(c, 10);
                    if (!isAdjacentToSymbol) {
                        isAdjacentToSymbol = getFirstNeighbouringSymbolPosition(arr, row, col, GearRatios::isSymbol).isPresent();
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
        return result;
    }

    private static char[][] loadData(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
            .stream().map(String::toCharArray)
            .toArray(char[][]::new);
    }

    private static Optional<String> getFirstNeighbouringSymbolPosition(char[][] arr, int row, int col, Function<Character, Boolean> symbolFunc) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i >= arr.length) {
                    continue;
                }
                if (j < 0 || j >= arr[0].length) {
                    continue;
                }
                char c = arr[i][j];
                if (symbolFunc.apply(c)) {
                    return Optional.of(i + "_" + j); // it means it's symbol
                }
            }
        }

        return Optional.empty();
    }

    private static boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private static boolean isAsteriskSymbol(char c) {
        return c == '*';
    }
}

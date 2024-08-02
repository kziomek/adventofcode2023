package advent.day14;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParabolicReflectorDish {

    // 14 cycles repetition pattern
    // 1000th elem is 103861
    // 1000 + 71428500 * 14 = 1000000000
    public static void main(String[] args) throws IOException {
        //        char[][] dish = Parser.parse("src/main/resources/day14/example.txt");
        char[][] dish = Parser.parse("src/main/resources/day14/my-input.txt");

        Map<String, Integer> seenMap = new HashMap<>();
        Map<Integer, Integer> weightMap = new HashMap<>();

        for (int i = 1; i <= 1000000000; i++) {
            tiltToNorth(dish);
            tiltToWest(dish);
            tiltToSouth(dish);
            tiltToEast(dish);
            //            print(dish);
            int weight = calculateWeight(dish);
            System.out.println(weight);

            String dishKey = calculateKey(dish);
            if (seenMap.containsKey(dishKey)) {
                int seenIdx = seenMap.get(dishKey);
                int ridx = seenIdx + (1000000000 - seenIdx) % (i - seenIdx);
                System.out.println("1000000000th weight " + weightMap.get(ridx));
                break;
            } else {
                seenMap.put(dishKey, i);
                weightMap.put(i, weight);
            }
        }
    }

    private static String calculateKey(char[][] dish) {
        return Arrays.stream(dish)
            .map(String::new)
            .collect(Collectors.joining());
    }

    private static int calculateWeight(char[][] dish) {
        int totalWeight = 0;
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[0].length; j++) {
                if (dish[i][j] == 'O') {
                    totalWeight += (dish.length - i);
                }
            }
        }
        return totalWeight;
    }

    private static void tiltToNorth(char[][] dish) {
        for (int col = 0; col < dish[0].length; col++) {
            rollNorth(dish, col);
        }
    }

    private static void rollNorth(char[][] dish, int col) {
        int i = 0, j = 0;
        while (j < dish.length) {
            if (i == j && (dish[i][col] == 'O' || dish[i][col] == '#')) {
                i++;
                j++;
                continue;
            }
            if (dish[j][col] == '.') {
                j++;
                continue;
            }

            if (dish[j][col] == 'O') {
                dish[i][col] = 'O';
                dish[j][col] = '.';
                i++;
                j++;
                continue;
            }
            if (dish[j][col] == '#') {
                j++;
                i = j;
            }
        }
    }

    private static void tiltToSouth(char[][] dish) {
        for (int col = 0; col < dish[0].length; col++) {
            rollSouth(dish, col);
        }
    }

    private static void rollSouth(char[][] dish, int col) {
        int i = dish.length - 1, j = dish.length - 1;
        while (j >= 0) {
            if (i == j && (dish[i][col] == 'O' || dish[i][col] == '#')) {
                i--;
                j--;
                continue;
            }
            if (dish[j][col] == '.') {
                j--;
                continue;
            }

            if (dish[j][col] == 'O') {
                dish[i][col] = 'O';
                dish[j][col] = '.';
                i--;
                j--;
                continue;
            }
            if (dish[j][col] == '#') {
                j--;
                i = j;
            }
        }
    }

    private static void tiltToWest(char[][] dish) {
        for (int row = 0; row < dish.length; row++) {
            rollWest(dish, row);
        }
    }

    private static void rollWest(char[][] dish, int row) {
        int i = 0, j = 0;
        while (j < dish.length) {
            if (i == j && (dish[row][i] == 'O' || dish[row][i] == '#')) {
                i++;
                j++;
                continue;
            }
            if (dish[row][j] == '.') {
                j++;
                continue;
            }

            if (dish[row][j] == 'O') {
                dish[row][i] = 'O';
                dish[row][j] = '.';
                i++;
                j++;
                continue;
            }
            if (dish[row][j] == '#') {
                j++;
                i = j;
            }
        }
    }

    private static void tiltToEast(char[][] dish) {
        for (int row = 0; row < dish.length; row++) {
            rollEast(dish, row);
        }
    }

    private static void rollEast(char[][] dish, int row) {
        int i = dish[0].length - 1, j = dish[0].length - 1;
        while (j >= 0) {
            if (i == j && (dish[row][i] == 'O' || dish[row][i] == '#')) {
                i--;
                j--;
                continue;
            }
            if (dish[row][j] == '.') {
                j--;
                continue;
            }

            if (dish[row][j] == 'O') {
                dish[row][i] = 'O';
                dish[row][j] = '.';
                i--;
                j--;
                continue;
            }
            if (dish[row][j] == '#') {
                j--;
                i = j;
            }
        }
    }

    private static void print(char[][] dish) {
        for (char[] rows : dish) {
            for (char cell : rows) {
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }
}

package advent.day14;

import java.io.IOException;
import java.nio.file.Path;

public class ParabolicReflectorDish {

    public static void main(String[] args) throws IOException {
//        char[][] dish = Parser.parse("src/main/resources/day14/example.txt");
        char[][] dish = Parser.parse("src/main/resources/day14/my-input.txt");
        tiltToNorht(dish);
        int weight = calculateWeight(dish);

        System.out.println(weight);
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

    private static void tiltToNorht(char[][] dish) {
        for (int col = 0; col < dish[0].length; col++) {
            roll(dish, col);
        }
    }

    private static void roll(char[][] dish, int col) {
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
}

package advent.day11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmicExpansion {

    public static void main(String[] args) throws IOException {
        //        char[][] data = Parser.parse("src/main/resources/day11/example.txt");
        char[][] data = Parser.parse("src/main/resources/day11/my-input.txt");

        int expandRatio = 1000000;
        int[] colExpander = Expander.colExpander(data, expandRatio);
        int[] rowExpander = Expander.rowExpander(data, expandRatio);

        print(data);

        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }
        }
        Galaxy[] gals = galaxies.toArray(Galaxy[]::new);

        long sum = 0;
        for (int i = 0; i < gals.length; i++) {
            for (int j = i + 1; j < gals.length; j++) {
                sum += Math.abs(gals[i].i + rowExpander[gals[i].i] - gals[j].i - rowExpander[gals[j].i])
                    + Math.abs(gals[i].j + colExpander[gals[i].j] - gals[j].j - colExpander[gals[j].j]);
            }
        }
        System.out.println("Sum " + sum);
    }

    private static void print(char[][] arr) {
        for (char[] chars : arr) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println("");
        }
    }
}

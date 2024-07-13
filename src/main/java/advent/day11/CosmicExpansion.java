package advent.day11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmicExpansion {

    public static void main(String[] args) throws IOException {
//        char[][] arr = Parser.parse("src/main/resources/day11/example.txt");
        char[][] arr = Parser.parse("src/main/resources/day11/my-input.txt");
        char[][] expanded = Expander.expand(arr);

        print(expanded);

        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < expanded.length; i++) {
            for (int j = 0; j < expanded[0].length; j++) {
                if (expanded[i][j] == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }
        }
        Galaxy[] gals = galaxies.toArray(Galaxy[]::new);

        long sum = 0;
        for (int i = 0; i < gals.length; i++) {
            for (int j = i + 1; j < gals.length; j++) {
                sum += Math.abs(gals[i].i - gals[j].i) + Math.abs(gals[i].j - gals[j].j);
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

package advent.day11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CosmicExpansion {

    public static void main(String[] args) throws IOException {
        char[][] arr = Parser.parse("src/main/resources/day11/example.txt");
        char[][] expanded = Expander.expand(arr);

        print(expanded);
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

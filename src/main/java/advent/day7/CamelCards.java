package advent.day7;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CamelCards {

    public static void main(String[] args) throws IOException {
//                List<Hand> hands = Parser.parse("src/main/resources/day7/example.txt");
        List<Hand> hands = Parser.parse("src/main/resources/day7/my-input.txt"); //251287184

        long totalWinning = part1(hands);

        System.out.println("Total winning: " + totalWinning);
    }

    private static long part1(List<Hand> hands) {
        Collections.sort(hands);

        long totalWinning = 0;
        for (int i = 0; i < hands.size(); i++) {
            totalWinning += hands.get(i).bid * (i + 1);
        }
        return totalWinning;
    }
}

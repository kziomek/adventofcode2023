package advent.day4;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11

 */
public class Scratchcard {

    public static void main(String[] args) throws IOException {
        //        List<Card> cards = Parser.parse("src/main/resources/day4/example-part1.txt");
        List<Card> cards = Parser.parse("src/main/resources/day4/my-input.txt");

        System.out.println(runPart1(cards));
        System.out.println(runPart2(cards));
    }

    private static int runPart1(List<Card> cards) {
        return cards
            .stream()
            .map(Card::cardValue)
            .mapToInt(i -> i)
            .sum();
    }

    private static int runPart2(List<Card> cards) {
        int[] cardCounts = new int[cards.size() + 1];
        for (int i = 1; i < cardCounts.length; i++) {
            cardCounts[i] = 1;
        }

        for (Card card : cards) {
            int id = card.getId();
            long value = card.matchingNumbersCount();

            for (int i = 1; i <= value; i++) {
                cardCounts[id + i] = cardCounts[id + i] + cardCounts[id];
            }
        }

        return Arrays.stream(cardCounts).sum();
    }
}

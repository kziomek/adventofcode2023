package advent.day4;

import java.util.Set;

/*
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
 */
public class Card {

    private int id;
    private Set<Integer> winningNumbers;
    private Set<Integer> numbersYouHave;

    public int getId() {
        return id;
    }

    public Card(Set<Integer> winningNumbers, Set<Integer> numbersYouHave) {
        this.winningNumbers = winningNumbers;
        this.numbersYouHave = numbersYouHave;
    }

    public Card(int id, Set<Integer> winningNumbers, Set<Integer> numbersYouHave) {
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.numbersYouHave = numbersYouHave;
    }

    public Integer cardValue() {
        long count = numbersYouHave
            .stream()
            .filter(n -> winningNumbers.contains(n))
            .count();
        if (count == 0) {
            return 0;
        }
        return (int) Math.pow(2, count - 1);
    }

    public long matchingNumbersCount() {
        return numbersYouHave
            .stream()
            .filter(n -> winningNumbers.contains(n))
            .count();
    }
}

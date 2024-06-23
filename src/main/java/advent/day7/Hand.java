package advent.day7;

import java.util.HashMap;
import java.util.Map;

/*
    Type:
    7 Five of a kind
    6 Four of a kind
    5 Full house
    4 Three of a kind
    3 Two pair
    2 One pair
    1 High card

    Labels:
    A -> 14
    K -> 13
    Q -> 12
    J -> 11
    T -> 10
    9, 8, 7, 6, 5, 4, 3, or 2
 */
public class Hand implements Comparable<Hand> {
    //    String hand;
    int[] hand = new int[5];
    int type;
    long bid;

    public Hand(String handInput, long bid) {
        hand[0] = convertLabel(handInput.charAt(0));
        hand[1] = convertLabel(handInput.charAt(1));
        hand[2] = convertLabel(handInput.charAt(2));
        hand[3] = convertLabel(handInput.charAt(3));
        hand[4] = convertLabel(handInput.charAt(4));

        this.bid = bid;

        this.type = calculateType(hand);
    }

    private int convertLabel(char label) {
        switch (label) {
            case 'A':
                return 14;
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                return 11;
            case 'T':
                return 10;
            default:
                return Character.digit(label, 10);
        }
    }

    /*
        7 Five of a kind    -> map x 5
        6 Four of a kind    -> map x4 y1
        5 Full house        -> map x3 y2
        4 Three of a kind   -> map x3 y1 z1
        3 Two pair          -> map x2 y2 z1
        2 One pair          -> map x2 y1 z1 a1
        1 High card         -> map x1 y1 z1 a1 b1
    */
    private int calculateType(int[] hand) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < hand.length; i++) {
            map.merge(hand[i], 1, Integer::sum);
        }

        if (map.size() == 1) {
            return 7;
        }

        if (map.size() == 2) {
            if (map.containsValue(4)) {
                return 6;
            } else {
                return 5;
            }
        }

        if (map.size() == 3) {
            if (map.containsValue(3)) {
                return 4;
            } else {
                return 3;
            }
        }

        if (map.size() == 4) {
            return 2;
        }

        return 1;
    }

    @Override
    public int compareTo(Hand o) {
        if (this.type == o.type) {
            // compare positions
            int i = 0;
            while (i < this.hand.length && this.hand[i] == o.hand[i]) {
                i++;
            }
            if (i < 5) {
                return this.hand[i] - o.hand[i];
            }
            return 0;
        } else {
            return this.type - o.type;
        }
    }
}

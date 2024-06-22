package advent.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class WaitForIt {

    public static void main(String[] args) throws IOException {

//        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/example.txt");
        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/my-input.txt");

        int result = recordRaces
            .stream()
            .map(WaitForIt::calculate)
            .mapToInt(i -> i)
            .filter(i -> i > 0)
            .reduce(1, (a, b) -> a * b);

        System.out.println(result);
    }

    private static int calculate(RecordRace recordRace) {
        int time = recordRace.getTime();
        int maxDistance = recordRace.getDistance();

        // count left
        int distance = 0;
        int left = 0;
        while (distance <= maxDistance && left < time) {
            left++;
            distance = left * (time - left);
        }
        System.out.println("Left " + left + " " + distance);

        // count right
        distance = 0;
        int right = time;
        while (distance <= maxDistance && right > 0) {
            right--;
            distance = right * (time - right);
        }

        System.out.println("right " + right + " " + distance);
        //        // count right
        //        9 current record -10 wins
        //        time 7
        //
        //        7 * 0
        //        6 * 1
        //        5 * 2
        //        4 * 3
        //        3 * 4
        //        2 * 5
        //        1 * 6
        //        0 * 7

        return Math.max(0, right - left + 1);
    }
}

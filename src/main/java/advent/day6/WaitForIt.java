package advent.day6;

import java.io.IOException;
import java.util.List;

public class WaitForIt {

    public static void main(String[] args) throws IOException {

        //        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/example.txt");
        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/my-input.txt"); // part-1 512295

        int result = recordRaces
            .stream()
            .map(WaitForIt::calculate)
            .mapToInt(i -> i)
            .filter(i -> i > 0)
            .reduce(1, (a, b) -> a * b);

        System.out.println(result);
    }

    /*
         https://www.youtube.com/watch?v=YYGlwDC-5gA&ab_channel=MarekDuda
         x * (time - x) - maxDistance = 0
         -x2 +x*time - maxDistance = 0
         delta = time*time - 4 * (-1 ) * maxDistance
         sqrt delta
         x1 =  (-time + sqrtDelta) / -2
         x2 =  (-time - sqrtDelta) / -2
     */

    private static int calculateWithQuadraticEquation(RecordRace recordRace) {
        int time = recordRace.getTime();
        int maxDistance = recordRace.getDistance();
        int delta = time * time - 4 * maxDistance;
        double sqrtDelta = Math.sqrt(delta);

        double x1 = (-time + sqrtDelta) / -2;
        double x2 = (-time - sqrtDelta) / -2;

        x1 = x1 == Math.floor(x1) ? x1 + 1 : Math.ceil(x1);
        x2 = x2 == Math.ceil(x2) ? x2 - 1 : Math.floor(x2);

        double r = x2 - x1 + 1;

        return (int) r;
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

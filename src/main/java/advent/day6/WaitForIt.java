package advent.day6;

import java.io.IOException;
import java.util.List;

public class WaitForIt {

    public static void main(String[] args) throws IOException {
        // ----------- PART 1 --------------
        //        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/example.txt");
        List<RecordRace> recordRaces = Parser.parse("src/main/resources/day6/my-input.txt"); // part-1 512295

        long result = recordRaces
            .stream()
            .map(WaitForIt::calculate)
            .mapToLong(i -> i)
            .filter(i -> i > 0)
            .reduce(1, (a, b) -> a * b);

        System.out.println(result);

        // ----------- PART 2 --------------
        RecordRace part2RaceExample = new RecordRace(71530, 940200);
        long part2ExampleResult = calculate(part2RaceExample);
        System.out.println("Part 2 Example result: " + part2ExampleResult);


        RecordRace part2Race = new RecordRace(46807866, 214117714021024L);
        long part2Result = calculate(part2Race);
        long part2ResultOfQuadraticEquation = calculateWithQuadraticEquation(part2Race);
        System.out.println("Part 2 result: " + part2Result);
        System.out.println("Part 2 result of quadratic equation: " + part2ResultOfQuadraticEquation);

        // part 2 result 36530883
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

    private static long calculateWithQuadraticEquation(RecordRace recordRace) {
        long time = recordRace.getTime();
        long maxDistance = recordRace.getDistance();
        long delta = time * time - 4 * maxDistance;
        double sqrtDelta = Math.sqrt(delta);

        double x1 = (-time + sqrtDelta) / -2;
        double x2 = (-time - sqrtDelta) / -2;

        x1 = x1 == Math.floor(x1) ? x1 + 1 : Math.ceil(x1);
        x2 = x2 == Math.ceil(x2) ? x2 - 1 : Math.floor(x2);

        double r = x2 - x1 + 1;

        return (long) r;
    }

    private static long calculate(RecordRace recordRace) {
        long time = recordRace.getTime();
        long maxDistance = recordRace.getDistance();

        // count left
        long distance = 0;
        long left = 0;
        while (distance <= maxDistance && left < time) {
            left++;
            distance = left * (time - left);
        }
        System.out.println("Left " + left + " " + distance);

        // count right
        distance = 0;
        long right = time;
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

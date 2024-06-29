package advent.day9;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MirageMaintenance {

    public static void main(String[] args) throws IOException {
        List<List<Long>> input = Parser.parse("src/main/resources/day9/example.txt");
//        List<List<Long>> input = Parser.parse("src/main/resources/day9/my-input.txt");
        long result = input.stream()
            .map(MirageMaintenance::extrapolate)
            .mapToLong(i -> i)
            .sum();

        System.out.println(result);
    }

    private static long extrapolate(List<Long> values) {
        if (allZeros(values)) {
            return 0;
        }
        List<Long> diffs = calculateDiffs(values);
        long result = extrapolate(diffs);
        return result + values.getLast();
    }

    private static List<Long> calculateDiffs(List<Long> values) {
        List<Long> diffs = new ArrayList<>();

        for (int i = 0; i < values.size() - 1; i++) {
            diffs.add(values.get(i + 1) - values.get(i));
        }
        return diffs;
    }

    private static boolean allZeros(List<Long> values) {
        return values.stream().allMatch(v -> v == 0);
    }
}

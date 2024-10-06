package advent.day18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class SweepLineAlgorithm {

    public static long calculateArea(List<Step> steps) {

        List<Line> lines = collectLines(steps);
        lines.sort(Comparator.comparingLong(line -> line.x));

        Map<Long, List<Line>> linesMap = new HashMap<>();
        for (Line line : lines) {
            if (!linesMap.containsKey(line.x)) {
                linesMap.put(line.x, new ArrayList<>());
            }
            linesMap.get(line.x).add(line);
        }
        System.out.println(linesMap);

        long area = 0;
        List<Line> sweepingLines = new ArrayList<>();
        for (Long x : linesMap.keySet().stream().sorted().toList()) {
            System.out.println("x " + x);
            System.out.println("sweepingLines " + sweepingLines);

            long sweptArea = sweptArea(x, sweepingLines);
            System.out.println("swept area: " + sweptArea);
            area += sweptArea;

            List<Line> currentLines = linesMap.get(x);

            List<Line> newSeepingLines = LineUtils.merge(sweepingLines, currentLines, x);
            long sweepingDifferenceArea = sweepingDifferenceArea(sweepingLines, newSeepingLines);
            System.out.println("sweepingDifferenceArea " + sweepingDifferenceArea);
            area += sweepingDifferenceArea;
            sweepingLines = newSeepingLines;
        }

        print(lines);
        return area;
    }

    private static long sweepingDifferenceArea(List<Line> sweepingLines, List<Line> newSeepingLines) {

        List<Line> remainingLines = LineUtils.subtractLines(sweepingLines, newSeepingLines);

        System.out.println("\nCalculate Reduction Difference");
        System.out.println(sweepingLines);
        System.out.println(newSeepingLines);

        return countTotalLength(remainingLines);
    }

    private static long countTotalLength(List<Line> remain) {
        long total = 0;
        for (Line line : remain) {
            total += line.b - line.a + 1;
        }

        return total;
    }

    private static long sweptArea(Long x, List<Line> sweeps) {
        long area = 0;
        for (Line sweep : sweeps) {
            area += (sweep.b - sweep.a + 1) * (x - sweep.x);
        }
        return area;
    }

    private static void print(List<Line> lines) {
        for (Line line : lines) {
            System.out.println(line);
        }
    }

    private static List<Line> collectLines(List<Step> steps) {
        long x = 0;
        long y = 0;
        List<Line> lines = new ArrayList<>();

        for (Step step : steps) {
            switch (step.getDirection()) {
                case 'R' -> x += step.getLength();
                case 'L' -> x -= step.getLength();
                case 'D' -> {
                    lines.add(new Line(x, y, y + step.getLength()));
                    y += step.getLength();
                }
                case 'U' -> {
                    lines.add(new Line(x, y - step.getLength(), y));
                    y -= step.getLength();
                }
            }
        }

        return lines;
    }
}

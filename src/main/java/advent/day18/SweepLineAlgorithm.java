package advent.day18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<Line> newSeepingLines = mergeSweepLinesWithNextLines(updateX(sweepingLines, x), currentLines);

            long sweepingDifferenceArea = sweepingDifferenceArea(sweepingLines, newSeepingLines);
            System.out.println("sweepingDifferenceArea " + sweepingDifferenceArea);
            area += sweepingDifferenceArea;
            sweepingLines = newSeepingLines;
        }

        print(lines);
        return area;
    }

    private static List<Line> updateX(List<Line> sweeps, Long x) {
        List<Line> mergedLines = new ArrayList<>();
        for (Line sweep : sweeps) {
            mergedLines.add(new Line(x, sweep.a, sweep.b));
        }
        return mergedLines;
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

    // This method is algorithm specific and needs to "switch off" line when it's met second time, meaning it's closing area
    public static List<Line> mergeSweepLinesWithNextLines(List<Line> sweepLines, List<Line> currentLines) {
        for (Line currentLine : currentLines) {
            Line overlappedLine = findOverlapped(currentLine, sweepLines);
            if (overlappedLine == null) {
                sweepLines.add(currentLine); //fully opening line
            } else {
                if (currentLine.a == overlappedLine.a && currentLine.b == overlappedLine.b) { // equals
                    sweepLines.remove(overlappedLine); // closing line
                } else if (currentLine.a > overlappedLine.a && currentLine.b < overlappedLine.b) { //  closing in
                    sweepLines.remove(overlappedLine);
                    sweepLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.a));
                    sweepLines.add(new Line(overlappedLine.x, currentLine.b, overlappedLine.b));
                } else if (currentLine.a == overlappedLine.a) { // reduce from a
                    sweepLines.remove(overlappedLine);
                    sweepLines.add(new Line(overlappedLine.x, currentLine.b, overlappedLine.b));
                } else if (currentLine.b == overlappedLine.b) { //reduce from b
                    sweepLines.remove(overlappedLine);
                    sweepLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.a));
                } else if (currentLine.b == overlappedLine.a) { //extend at up
                    sweepLines.remove(overlappedLine);
                    sweepLines.add(new Line(overlappedLine.x, currentLine.a, overlappedLine.b));
                } else if (currentLine.a == overlappedLine.b) { // extend at bottom
                    sweepLines.remove(overlappedLine);
                    sweepLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.b));
                }
            }
        }
        System.out.println("sweepLines " + sweepLines);

        sweepLines = LineUtils.joinAdjacent(sweepLines);
        System.out.println("joined sweepLines " + sweepLines);
        return sweepLines;
    }

    private static Line findOverlapped(Line currentLine, List<Line> mergedLines) {
        for (Line mergedLine : mergedLines) {
            if (currentLine.a >= mergedLine.a && currentLine.a <= mergedLine.b
                || currentLine.b >= mergedLine.a && currentLine.b <= mergedLine.b
            ) {
                return mergedLine;
            }
        }
        return null;
    }
}

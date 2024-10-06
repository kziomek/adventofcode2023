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
    public static int calculateArea(List<Step> steps) {

        List<Line> lines = collectLines(steps);
        lines.sort(Comparator.comparingInt(line -> line.x));

        Map<Integer, List<Line>> linesMap = new HashMap<>();
        for (Line line : lines) {
            if (!linesMap.containsKey(line.x)) {
                linesMap.put(line.x, new ArrayList<>());
            }
            linesMap.get(line.x).add(line);
        }
        System.out.println(linesMap);

        int area = 0;
        List<Line> sweepingLines = new ArrayList<>();
        for (Integer x : linesMap.keySet().stream().sorted().toList()) {
            System.out.println("x " + x);
            System.out.println("sweepingLines " + sweepingLines);

            int sweptArea = sweptArea(x, sweepingLines);
            System.out.println("swept area: " + sweptArea);
            area += sweptArea;

            List<Line> currentLines = linesMap.get(x);

            List<Line> newSeepingLines = merge(sweepingLines, currentLines, x);
            int sweepingDifferenceArea = sweepingDifferenceArea(sweepingLines, newSeepingLines);
            System.out.println("sweepingDifferenceArea " + sweepingDifferenceArea);
            area += sweepingDifferenceArea;
            sweepingLines = newSeepingLines;

            //            currentLines.sort(Comparator.comparingInt(line -> line.a.y));

            //            System.out.println(currentLines);

        }

        //get next lines
        //
        //        area +=sweptArea(sweepingLines);
        //
        //        List<Line> newSweeps = merge(sweepingLines, currentLInes);
        //        area+=addSweepingDifference(sweepingLines, newSweeps);

        print(lines);

        //sort lines by x

        //sweep

        return area;
    }

    private static int sweepingDifferenceArea(List<Line> sweepingLines, List<Line> newSeepingLines) {

        List<Line> remainingLines = subtractLines(sweepingLines, newSeepingLines);

        System.out.println("\nCalculate Reduction Difference");
        System.out.println(sweepingLines);
        System.out.println(newSeepingLines);

        return countTotalLength(remainingLines);


//
//        //todo FIX ME x=7 - reimplement this method
//        // sweeping line can be cat into smaller lines. All cutted lines must be processed before producing remingin
//
//        List<Line> remain = new ArrayList<>();
//        for (Line sweepingLine : sweepingLines) {
//
//            int ay = sweepingLine.a;
//            int by = sweepingLine.b;
//            for (Line newSeepingLine : newSeepingLines) {
//
//                if (!isOverlapping(sweepingLine, newSeepingLine)) {
//                    continue;
//                } else if (sweepingLine.a == newSeepingLine.a && newSeepingLine.b < sweepingLine.b) { // was reduction
//                    ay = newSeepingLine.b + 1;
//                } else if (sweepingLine.b == newSeepingLine.b && newSeepingLine.a > sweepingLine.a) { //was reduction
//                    by = newSeepingLine.a - 1;
//                } else if (sweepingLine.a < newSeepingLine.a && sweepingLine.b >= newSeepingLine.a && sweepingLine.b < newSeepingLine.b) { //overlaping reduction down
//                    by = newSeepingLine.a - 1;
//                } else if (sweepingLine.b > newSeepingLine.b && sweepingLine.a <= newSeepingLine.b && sweepingLine.a > newSeepingLine.a) { //overlaping reduction up
//                    ay = newSeepingLine.b + 1;
//                } else { //overlaping
//
//                    if (sweepingLine.a < newSeepingLine.a && sweepingLine.b > newSeepingLine.b) {
//                        remain.add(new Line(sweepingLine.x, sweepingLine.a, newSeepingLine.a - 1));
//                        remain.add(new Line(sweepingLine.x, newSeepingLine.b + 1, sweepingLine.b));
//                    }
//                    ay = 2;
//                    by = 1;
//                }
//            }
//
//            if (ay <= by) {
//                remain.add(new Line(sweepingLine.x, ay, by));
//            }
//        }
//
//        System.out.println("Reduced line ");
//        System.out.println(remain);
//        return countTotalLength(remain);
    }

    public static List<Line> subtractLines(Line minuend, Line subtrahend) {
        if (!isOverlapping(minuend, subtrahend)) {
            return List.of(minuend);
        }
        // minuend longer than subtrahend on both ends will be cut in the middle into two lines
        if (minuend.a < subtrahend.a && minuend.b > subtrahend.b) {
            return List.of(new Line(minuend.x, minuend.a, subtrahend.a - 1), new Line(minuend.x, subtrahend.b + 1, minuend.b));
        }
        // minuend completely covered by subtrahend
        if (minuend.a >= subtrahend.a && minuend.b <= subtrahend.b) {
            return List.of();
        }
        // minuend protruding on left end
        if (minuend.b >= subtrahend.a && minuend.b <= subtrahend.b) {
            return List.of(new Line(minuend.x, minuend.a, subtrahend.a - 1));
        }

        // minuend protruding on right end
        if (minuend.a >= subtrahend.a && minuend.a <= subtrahend.b) {
            return List.of(new Line(minuend.x, subtrahend.b + 1, minuend.b));
        }
        throw new IllegalStateException("should not be here");
    }

    public static List<Line> subtractLines(List<Line> minuends, List<Line> subtrahends) {
        List<Line> remainingLines = new ArrayList<>(minuends);
        for (Line subtrahend : subtrahends) {
            Queue<Line> queue = new LinkedList<>(remainingLines);
            remainingLines.clear();

            while (!queue.isEmpty()) {
                List<Line> subtractedLines = subtractLines(queue.poll(), subtrahend);
                remainingLines.addAll(subtractedLines);
            }
        }
        return remainingLines;
    }

    private static int countTotalLength(List<Line> remain) {
        int total = 0;
        for (Line line : remain) {
            total += line.b - line.a + 1;
        }

        return total;
    }

    private static boolean isOverlapping(Line sweepingLine, Line newSeepingLine) {
        return sweepingLine.a >= newSeepingLine.a && sweepingLine.a <= newSeepingLine.b
            || sweepingLine.b >= newSeepingLine.a && sweepingLine.b <= newSeepingLine.b
            || newSeepingLine.a >= sweepingLine.a && newSeepingLine.a <= sweepingLine.b
            || newSeepingLine.b >= sweepingLine.a && newSeepingLine.b <= sweepingLine.b;
    }

    private static List<Line> merge(List<Line> sweeps, List<Line> currentLines, int x) {
        System.out.println("\nMerge at x = " + x);
        System.out.println("sweeps " + sweeps);
        System.out.println("currentLines " + currentLines);

        List<Line> mergedLines = new ArrayList<>();
        for (Line sweep : sweeps) {
            mergedLines.add(new Line(x, sweep.a, sweep.b));
        }
        for (Line currentLine : currentLines) {
            Line overlappedLine = findOverlapped(currentLine, mergedLines);
            if (overlappedLine != null) {
                if (currentLine.a == overlappedLine.a && currentLine.b == overlappedLine.b) { // equals
                    mergedLines.remove(overlappedLine);
                } else if (currentLine.a > overlappedLine.a && currentLine.b < overlappedLine.b) { //  in
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.a));
                    mergedLines.add(new Line(overlappedLine.x, currentLine.b, overlappedLine.b));
                } else if (currentLine.a == overlappedLine.a) { // reduce from a
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(overlappedLine.x, currentLine.b, overlappedLine.b));
                } else if (currentLine.b == overlappedLine.b) { //reduce from b
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.a));
                } else if (currentLine.b == overlappedLine.a) { //extend at up
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(overlappedLine.x, currentLine.a, overlappedLine.b));
                } else if (currentLine.a == overlappedLine.b) { // extend at bottom
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(overlappedLine.x, overlappedLine.a, currentLine.b));
                }
            } else {
                mergedLines.add(currentLine);
            }
        }
        System.out.println("mergedLines " + mergedLines);

        mergedLines = joinAdjacent(mergedLines);
        System.out.println("joined mergedLines " + mergedLines);
        return mergedLines;

        //join adjacent

        //if overlap with sweep, split sweep
        // if touches on one end and overlap -> reduce sweep
        // if touches on one end and do not overlap -> add to sweep
        // if not overlap add line to sweeps
    }

    private static List<Line> joinAdjacent(List<Line> mergedLines) {
        if (mergedLines.isEmpty()) {
            return mergedLines;
        }

        PriorityQueue<Line> queue = new PriorityQueue<>(Comparator.comparingInt(line -> line.a));
        queue.addAll(mergedLines);

        List<Line> joinedLines = new ArrayList<>();

        Line firstLine = null;
        while (!queue.isEmpty()) {
            if (firstLine == null) {
                firstLine = queue.poll();
                if (queue.isEmpty()) {
                    joinedLines.add(firstLine);
                }
                continue;
            }
            Line secondLine = queue.poll();
            if (firstLine.b == secondLine.a) {
                firstLine = new Line(firstLine.x, firstLine.a, secondLine.b);
            } else {
                joinedLines.add(firstLine);
                firstLine = secondLine;
            }
            if (queue.isEmpty()) {
                joinedLines.add(firstLine);
            }
        }
        return joinedLines;
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

    private static int sweptArea(Integer x, List<Line> sweeps) {
        int area = 0;
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
        int x = 0;
        int y = 0;
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

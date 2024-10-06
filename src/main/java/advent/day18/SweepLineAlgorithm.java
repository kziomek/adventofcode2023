package advent.day18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SweepLineAlgorithm {
    public static int calculateArea(List<Step> steps) {

        List<Line> lines = collectLines(steps);
        lines.sort(Comparator.comparingInt(line -> line.a.x));

        Map<Integer, List<Line>> linesMap = new HashMap<>();
        for (Line line : lines) {
            if (!linesMap.containsKey(line.a.x)) {
                linesMap.put(line.a.x, new ArrayList<>());
            }
            linesMap.get(line.a.x).add(line);
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
        subtractLines(sweepingLines, newSeepingLines);

        System.out.println("\nCalculate Reduction Difference");
        System.out.println(sweepingLines);
        System.out.println(newSeepingLines);

        //todo FIX ME x=7 - reimplement this method
        // sweeping line can be cat into smaller lines. All cutted lines must be processed before producing remingin

        List<Line> remain = new ArrayList<>();
        for (Line sweepingLine : sweepingLines) {

            int ay = sweepingLine.a.y;
            int by = sweepingLine.b.y;
            for (Line newSeepingLine : newSeepingLines) {

                if (!isOverlapping(sweepingLine, newSeepingLine)) {
                    continue;
                } else if (sweepingLine.a.y == newSeepingLine.a.y && newSeepingLine.b.y < sweepingLine.b.y) { // was reduction
                    ay = newSeepingLine.b.y + 1;
                } else if (sweepingLine.b.y == newSeepingLine.b.y && newSeepingLine.a.y > sweepingLine.a.y) { //was reduction
                    by = newSeepingLine.a.y - 1;
                } else if (sweepingLine.a.y < newSeepingLine.a.y && sweepingLine.b.y >= newSeepingLine.a.y &&  sweepingLine.b.y < newSeepingLine.b.y ) { //overlaping reduction down
                    by = newSeepingLine.a.y - 1;
                } else if (sweepingLine.b.y > newSeepingLine.b.y && sweepingLine.a.y <= newSeepingLine.b.y && sweepingLine.a.y > newSeepingLine.a.y) { //overlaping reduction up
                    ay = newSeepingLine.b.y + 1;
                } else { //overlaping

                    if (sweepingLine.a.y < newSeepingLine.a.y && sweepingLine.b.y > newSeepingLine.b.y) {
                        remain.add(new Line(new Point(sweepingLine.a.x, sweepingLine.a.y), new Point(sweepingLine.a.x, newSeepingLine.a.y - 1)));
                        remain.add(new Line(new Point(sweepingLine.a.x, newSeepingLine.b.y + 1), new Point(sweepingLine.a.x, sweepingLine.b.y)));
                    }
                    ay = 2;
                    by = 1;
                }
            }

            if (ay <= by) {
                remain.add(new Line(new Point(sweepingLine.a.x, ay), new Point(sweepingLine.a.x, by)));
            }
        }

        System.out.println("Reduced line ");
        System.out.println(remain);
        return countTotalLength(remain);
    }

    public static List<Line> subtractLines(Line minuend, Line subtrahend) {
        return null;
    }

    public static List<Line> subtractLines(List<Line> minuend, List<Line> subtrahend) {
        return new ArrayList<>();
    }

    private static int countTotalLength(List<Line> remain) {
        int total = 0;
        for (Line line : remain) {
            total += line.b.y - line.a.y + 1;
        }

        return total;
    }

    private static boolean isOverlapping(Line sweepingLine, Line newSeepingLine) {
        return sweepingLine.a.y >= newSeepingLine.a.y && sweepingLine.a.y <= newSeepingLine.b.y
            || sweepingLine.b.y >= newSeepingLine.a.y && sweepingLine.b.y <= newSeepingLine.b.y
            || newSeepingLine.a.y >= sweepingLine.a.y && newSeepingLine.a.y <= sweepingLine.b.y
            || newSeepingLine.b.y >= sweepingLine.a.y && newSeepingLine.b.y <= sweepingLine.b.y;
    }

    private static List<Line> merge(List<Line> sweeps, List<Line> currentLines, int x) {
        System.out.println("\nMerge at x = " + x);
        System.out.println("sweeps " + sweeps);
        System.out.println("currentLines " + currentLines);

        List<Line> mergedLines = new ArrayList<>();
        for (Line sweep : sweeps) {
            mergedLines.add(new Line(new Point(x, sweep.a.y), new Point(x, sweep.b.y)));
        }
        for (Line currentLine : currentLines) {
            Line overlappedLine = findOverlapped(currentLine, mergedLines);
            if (overlappedLine != null) {
                if (currentLine.a.y == overlappedLine.a.y && currentLine.b.y == overlappedLine.b.y) { // equals
                    mergedLines.remove(overlappedLine);
                } else if (currentLine.a.y > overlappedLine.a.y && currentLine.b.y < overlappedLine.b.y) { //  in
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, overlappedLine.a.y), new Point(overlappedLine.a.x, currentLine.a.y)));
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, currentLine.b.y), new Point(overlappedLine.a.x, overlappedLine.b.y)));
                } else if (currentLine.a.y == overlappedLine.a.y) { // reduce from a
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, currentLine.b.y), new Point(overlappedLine.a.x, overlappedLine.b.y)));
                } else if (currentLine.b.y == overlappedLine.b.y) { //reduce from b
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, overlappedLine.a.y), new Point(overlappedLine.a.x, currentLine.a.y)));
                } else if (currentLine.b.y == overlappedLine.a.y) { //extend at up
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, currentLine.a.y), new Point(overlappedLine.a.x, overlappedLine.b.y)));
                } else if (currentLine.a.y == overlappedLine.b.y) { // extend at bottom
                    mergedLines.remove(overlappedLine);
                    mergedLines.add(new Line(new Point(overlappedLine.a.x, overlappedLine.a.y), new Point(overlappedLine.a.x, currentLine.b.y)));
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

        PriorityQueue<Line> queue = new PriorityQueue<>(Comparator.comparingInt(line -> line.a.y));
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
            if (firstLine.b.y == secondLine.a.y) {
                firstLine = new Line(new Point(firstLine.a.x, firstLine.a.y), new Point(firstLine.a.x, secondLine.b.y));
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
            if (currentLine.a.y >= mergedLine.a.y && currentLine.a.y <= mergedLine.b.y
                || currentLine.b.y >= mergedLine.a.y && currentLine.b.y <= mergedLine.b.y
            ) {
                return mergedLine;
            }
        }
        return null;
    }

    private static int sweptArea(Integer x, List<Line> sweeps) {
        int area = 0;
        for (Line sweep : sweeps) {
            area += (sweep.b.y - sweep.a.y + 1) * (x - sweep.a.x);
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
                    lines.add(new Line(new Point(x, y), new Point(x, y + step.getLength())));
                    y += step.getLength();
                }
                case 'U' -> {
                    lines.add(new Line(new Point(x, y - step.getLength()), new Point(x, y)));
                    y -= step.getLength();
                }
            }
        }

        return lines;
    }
}

package advent.day18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class LineUtils {

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

    public static List<Line> joinAdjacent(List<Line> lines) {
        if (lines.isEmpty()) {
            return lines;
        }

        PriorityQueue<Line> queue = new PriorityQueue<>(Comparator.comparingLong(line -> line.a));
        queue.addAll(lines);

        List<Line> joinedLines = new ArrayList<>();

        Line current = null;
        while (!queue.isEmpty()) {
            if (current == null) {
                current = queue.poll();
                if (queue.isEmpty()) {
                    joinedLines.add(current);
                }
                continue;
            }
            Line nextLine = queue.poll();
            if (current.b == nextLine.a) {
                current = new Line(current.x, current.a, nextLine.b);
            } else {
                joinedLines.add(current);
                current = nextLine;
            }
            if (queue.isEmpty()) {
                joinedLines.add(current);
            }
        }
        return joinedLines;
    }

    private static boolean isOverlapping(Line sweepingLine, Line newSeepingLine) {
        return sweepingLine.a >= newSeepingLine.a && sweepingLine.a <= newSeepingLine.b
            || sweepingLine.b >= newSeepingLine.a && sweepingLine.b <= newSeepingLine.b
            || newSeepingLine.a >= sweepingLine.a && newSeepingLine.a <= sweepingLine.b
            || newSeepingLine.b >= sweepingLine.a && newSeepingLine.b <= sweepingLine.b;
    }
}

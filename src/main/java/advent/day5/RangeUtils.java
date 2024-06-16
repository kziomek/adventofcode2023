package advent.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RangeUtils {

    public static void main(String[] args) {
        List<Range> list1 = new ArrayList<>();
        list1.add(new Range(0, Long.MAX_VALUE));

        List<Range> list2 = new ArrayList<>();
        list2.add(new Range(60, 96));
        list2.add(new Range(56, 59));
        list2.add(new Range(0, 1));

        List<Range> result = flatten(list1, list2);
        print(result);
    }

    public static List<Range> flatten(List<Range> list1, List<Range> list2) {
        System.out.println("list 1:");
        print(list1);
        System.out.println("list 2:");
        print(list2);

        List<Position> positions = new ArrayList<>();
        for (Range range : list1) {
            positions.add(new Position(range.start, true));
            positions.add(new Position(range.end, false));
        }

        for (Range range : list2) {
            positions.add(new Position(range.start, true));
            positions.add(new Position(range.end, false));
        }

        Collections.sort(positions);

        List<Range> flattenedRanges = new ArrayList<>();
        for (int i = 0; i < positions.size() - 1; i++) {
            Position a = positions.get(i);
            Position b = positions.get(i + 1);

            if (a.pos == Long.MAX_VALUE && b.pos == Long.MAX_VALUE) {
                continue;
            }

            long left = a.isStart ? a.pos : a.pos + 1;
            long right = b.isStart ? b.pos - 1 : b.pos;
            if (left <= right) {
                flattenedRanges.add(new Range(left, right));
            }
        }

        System.out.println("Flattened lists:");
        print(flattenedRanges);

        return flattenedRanges;
    }

    public static void print(List<Range> ranges) {
        for (Range range : ranges) {
            System.out.println(range.start + " " + range.end);
        }
        System.out.println();
    }
}

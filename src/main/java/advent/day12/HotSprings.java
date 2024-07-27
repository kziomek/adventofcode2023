package advent.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HotSprings {

    static int operationCount = 0;
    static int hits = 0;

    public static void main(String[] args) throws IOException {

        //        List<String> inputs = Collections.singletonList(".??..??...?##. 1,1,3"); // 63  operations
        //        List<String> inputs = Collections.singletonList("??????????#??.?????? 3,4,2,1,1,1"); // 524287  operations
        //        List<String> inputs = Collections.singletonList("????.#...#...?????.#...#...?????.#...#...?????.#...#...?????.#...#... 4,1,1,4,1,1,4,1,1,4,1,1,4,1,1"); // 524287  operations
        //        List<String> inputs = Collections.singletonList("?###??????????###??????????###??????????###??????????###???????? 3,2,1,3,2,1,3,2,1,3,2,1,3,2,1"); // 524287  operations

        List<String> inputs = Files.readAllLines(Path.of("src/main/resources/day12/example.txt"));
//                List<String> inputs = Files.readAllLines(Path.of("src/main/resources/day12/my-input.txt"));

        long resultCount = 0;
        for (String input : inputs) {
            System.out.println("Process: " + input);
            RowRecord record = new RowRecord(input);
            resultCount += matches(record, new HashMap<>());
        }

        System.out.println("Result " + resultCount);
        System.out.println("Operation count " + operationCount);
        System.out.println("Hits " + hits);
    }

    public static long matches(RowRecord record, HashMap<String, Long> mem) {
        operationCount++;
        record.print();

        if (record.isFirstGroupBiggerThenExpected()) {
            return 0;
        }

        int groupSize = record.firstGroupSize();

        if (groupSize == 0) {
            return record.validationList.isEmpty() ? 1 : 0;
        }

        // if first group completed
        if (groupSize > 0) {
            Integer expectedGroupSize = record.validationList.poll();
            record.removeLeadingGroup();
            return expectedGroupSize != null && expectedGroupSize == groupSize ? matches(record, mem) : 0;
        }

        //if not completed
        if (groupSize == -1) {
            String conditionRecord = record.conditionRecord;

            if (mem.containsKey(record.key())) {
                hits++;
                return mem.get(record.key());
            }
            long result = matches(new RowRecord(conditionRecord.replaceFirst("\\?", "."), new LinkedList<>(record.validationList)), mem)
                + matches(new RowRecord(conditionRecord.replaceFirst("\\?", "#"), new LinkedList<>(record.validationList)), mem);
            mem.put(record.key(), result);
            return result;
        }

        throw new IllegalStateException("unexpected");
    }
}

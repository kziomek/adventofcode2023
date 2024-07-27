package advent.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HotSprings {

    static int operationCount = 0;

    public static void main(String[] args) throws IOException {
        long resultCount = 0;
        int inputCount = 0;
        //        List<String> inputs = Collections.singletonList(".??..??...?##. 1,1,3"); // 63  operations
        //        List<String> inputs = Collections.singletonList("?###???????? 3,2,1");
        //                List<String> inputs = Collections.singletonList("??????????#??.?????? 3,4,2,1,1,1"); // 524287  operations
        //                        List<String> inputs = Collections.singletonList("????.#...#...?????.#...#...?????.#...#...?????.#...#...?????.#...#... 4,1,1,4,1,1,4,1,1,4,1,1,4,1,1"); // 524287  operations
//        List<String> inputs = Collections.singletonList("?###??????????###??????????###??????????###??????????###???????? 3,2,1,3,2,1,3,2,1,3,2,1,3,2,1"); // 524287  operations
//                                List<String> inputs = Files.readAllLines(Path.of("src/main/resources/day12/example.txt"));

                                List<String> inputs = Files.readAllLines(Path.of("src/main/resources/day12/my-input.txt"));
        for (String input : inputs) {
            System.out.println("Process: " + input);
            RowRecord record = new RowRecord(input);
            record.unfold();
            resultCount += matches(record, new HashMap<>());
        }

        System.out.println(resultCount);
        System.out.println(operationCount);
    }

    public static void count(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '?') {
                count++;
            }
        }
        if (count > 17) {
            System.out.println(count);
        }
    }

    public static long matches(RowRecord record, HashMap<String, Long> mem) {

        record.print();
        record.removeLeadingDots();
        operationCount++;
        int groupSize = record.firstGroupSize();

        if (groupSize == 0) {
            return record.validationList.isEmpty() ? 1 : 0;
        }

        if (groupSize > 0) {
            Integer expectedGroupSize = record.validationList.poll();
            record.removeLeadingGroup();
            return expectedGroupSize != null && expectedGroupSize == groupSize ? matches(record, mem) : 0;
        }

        if (groupSize == -1) {
            String conditionRecord = record.conditionRecord;

            if (mem.containsKey(record.key())) {
                System.out.println("Hit cache");
                return mem.get(record.key());
            }
            long result = matches(new RowRecord(conditionRecord.replaceFirst("\\?", "."), new LinkedList<>(record.validationList)), mem)
                + matches(new RowRecord(conditionRecord.replaceFirst("\\?", "#"), new LinkedList<>(record.validationList)), mem);
            mem.put(record.key(), result);
            return result;
        }

        //        System.out.println(input);
        //        if (!input.contains("?")) {
        //            return new RowRecord(input).isValid() ? 1 : 0;
        //        }
        //
        //        return matches(input.replaceFirst("\\?", ".")) + matches(input.replaceFirst("\\?", "#"));
        throw new IllegalStateException("unexpected");
    }
}

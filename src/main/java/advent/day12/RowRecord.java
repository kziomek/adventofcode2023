package advent.day12;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RowRecord {

    String conditionRecord;
    Queue<Integer> validationList;

    public RowRecord(String input) {
        String[] parts = input.split(" ");
        validationList = new LinkedList<>(Arrays.stream(parts[1].split(",")).map(Integer::valueOf).toList());
        conditionRecord = parts[0];
        unfold();
        removeLeadingDots();
    }

    public RowRecord(String conditionRecord, Queue<Integer> validationList) {
        this.conditionRecord = conditionRecord;
        this.validationList = validationList;
        removeLeadingDots();
    }

    public void removeLeadingGroup() {
        int i = 0;
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '#') {
            i++;
        }
        conditionRecord = conditionRecord.substring(i);
        removeLeadingDots();
    }

    public void removeLeadingDots() {
        int i = 0;
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '.') {
            i++;
        }
        conditionRecord = conditionRecord.substring(i);
    }

    // return -1 when ? present in group
    public int firstGroupSize() {
        int i = 0;

        if (i == conditionRecord.length()) {
            return 0;
        }

        int groupSize = 0;
        for (int j = i; j < conditionRecord.length(); j++) {
            if (conditionRecord.charAt(j) == '#') {
                groupSize++;
            }
            if (conditionRecord.charAt(j) == '.') {
                break;
            }
            if (conditionRecord.charAt(j) == '?') {
                return -1; // group not ready
            }
        }
        return groupSize;
    }

    public void print() {
        System.out.println(key());
    }

    public String key() {
        return conditionRecord + " " + validationList;
    }

    public void unfold() {
        conditionRecord = conditionRecord + "?" + conditionRecord + "?" + conditionRecord + "?" + conditionRecord + "?" + conditionRecord;
        LinkedList<Integer> list = new LinkedList<>();
        list.addAll(validationList);
        list.addAll(validationList);
        list.addAll(validationList);
        list.addAll(validationList);
        list.addAll(validationList);

        validationList = list;
    }

    public boolean isFirstGroupBiggerThenExpected() {
        int count = 0;
        int i = 0;
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '#') {
            count++;
            i++;
        }

        int max = validationList.peek() == null ? 0 : validationList.peek();
        return count > max;
    }

}

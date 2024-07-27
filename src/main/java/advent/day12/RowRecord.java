package advent.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RowRecord {

    String conditionRecord;
    Queue<Integer> validationList;

    public RowRecord(String input) {
        String[] parts = input.split(" ");
        validationList = new LinkedList<>(Arrays.stream(parts[1].split(",")).map(Integer::valueOf).toList());
        conditionRecord = parts[0];
    }

    public RowRecord(String conditionRecord, Queue<Integer> validationList) {
        this.conditionRecord = conditionRecord;
        this.validationList = validationList;
    }

    public void removeLeadingGroup() {
        int i = 0;
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '#') {
            i++;
        }
        conditionRecord = conditionRecord.substring(i);
    }

    public void removeLeadingDots() {
        int i = 0;
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '.') {
            i++;
        }
        conditionRecord = conditionRecord.substring(i);
        //        if (i == conditionRecord.length() ){
        //            conditionRecord = "";
        //        } else {
        //            conditionRecord = conditionRecord.substring(i);
        //        }

    }

    // return -1 when ? present in group
    public int firstGroupSize() {

        int i = 0;

        // skip .
        while (i < conditionRecord.length() && conditionRecord.charAt(i) == '.') {
            i++;
        }

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

    //    public boolean isValid() {
    //        List<Integer> groups = new ArrayList<>();
    //
    //        int count = 0;
    //        for (int i = 0; i < conditionRecord.length(); i++) {
    //            char c = conditionRecord.charAt(i);
    //
    //            if (c == '#') {
    //                count++;
    //            }
    //            if (c == '.' && count > 0) {
    //                groups.add(count);
    //                count = 0;
    //            }
    //        }
    //        if (count > 0) {
    //            groups.add(count);
    //        }
    //
    //        if (validationList.size() != groups.size()) {
    //            return false;
    //        }
    //        for (int i = 0; i < validationList.size(); i++) {
    //            if (validationList.get(i) != groups.get(i)) {
    //                return false;
    //            }
    //        }
    //
    //        return true;
    //    }
}

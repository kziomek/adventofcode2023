package advent.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RowRecord {

    String conditionRecord;
    List<Integer> validationList;

    public RowRecord(String input) {
        String[] parts = input.split(" ");
        validationList = Arrays.stream(parts[1].split(",")).map(Integer::valueOf).toList();
        conditionRecord = parts[0];
    }

    public boolean isValid() {
        List<Integer> groups = new ArrayList<>();

        int count = 0;
        for (int i = 0; i < conditionRecord.length(); i++) {
            char c = conditionRecord.charAt(i);

            if (c == '#') {
                count++;
            }
            if (c == '.' && count > 0) {
                groups.add(count);
                count = 0;
            }
        }
        if (count > 0) {
            groups.add(count);
        }

        if (validationList.size() != groups.size()) {
            return false;
        }
        for (int i = 0; i < validationList.size(); i++) {
            if (validationList.get(i) != groups.get(i)) {
                return false;
            }
        }

        return true;
    }
}

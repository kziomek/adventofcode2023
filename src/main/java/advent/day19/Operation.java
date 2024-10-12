package advent.day19;

import java.util.Map;

public class Operation {
    char rating;
    char oper;
    long value;
    String targetWorkflow;

    public Operation(char rating, char oper, Long value, String targetWorkflow) {
        this.rating = rating;
        this.oper = oper;
        this.value = value;
        this.targetWorkflow = targetWorkflow;
    }

    public boolean applies(Map<Character, Long> part) {
        if (rating == 'z') {
            return true;
        }

        long partValue = part.get(rating);
        return oper == '>' ? partValue > value : partValue < value;
    }

    @Override
    public String toString() {
        return "{" +
            "rating=" + rating +
            ", oper=" + oper +
            ", value=" + value +
            ", targetWorkflow='" + targetWorkflow + '\'' +
            '}';
    }

    public boolean isDummy() {
        return oper == 'z';
    }
}

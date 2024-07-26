package advent.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HotSprings {

    public static void main(String[] args) throws IOException {
        int resultCount = 0;
        int inputCount = 0;
        List<String> inputs = Files.readAllLines(Path.of("src/main/resources/day12/my-input.txt"));
        for (String input : inputs) {
            inputCount++;
            System.out.println(input);
            System.out.println(inputCount);
            resultCount += matches(input);
        }

        System.out.println(resultCount);
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

    public static int matches(String input) {
//        System.out.println(input);
        if (!input.contains("?")) {
            return new RowRecord(input).isValid() ? 1 : 0;
        }

        return matches(input.replaceFirst("\\?", ".")) + matches(input.replaceFirst("\\?", "#"));
    }
}

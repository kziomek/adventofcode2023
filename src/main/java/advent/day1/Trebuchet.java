package advent.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Trebuchet {

    public static void main(String[] args) throws IOException {
        long result = Files.readAllLines(Path.of("src/main/resources/day1/my-input.txt"))
            .stream().map(Trebuchet::findNumber)
            .mapToInt(Integer::intValue)
            .sum();

        System.out.println(result);
    }

    private static int findNumber(String line) {
        int leftDigit = 0;
        int rightDigit = 0;

        for (int i = 0; i < line.length(); i++) {
            int number = tryExtractingDigit(line, i);
            if (number > 0) {
                leftDigit = number;
                break;
            }
        }

        for (int i = line.length() - 1; i >= 0; i--) {
            int textNumber = tryExtractingDigit(line, i);
            if (textNumber > 0) {
                rightDigit = textNumber;
                break;
            }
        }

        return 10 * leftDigit + rightDigit;
    }

    private static int tryExtractingDigit(String line, int i) {
        try {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                return Character.digit(c, 10);
            }

            if (isTextDigitAt("one", line, i)) {
                return 1;
            }
            if (isTextDigitAt("two", line, i)) {
                return 2;
            }
            if (isTextDigitAt("six", line, i)) {
                return 6;
            }
            if (isTextDigitAt("four", line, i)) {
                return 4;
            }
            if (isTextDigitAt("five", line, i)) {
                return 5;
            }
            if (isTextDigitAt("nine", line, i)) {
                return 9;
            }
            if (isTextDigitAt("three", line, i)) {
                return 3;
            }
            if (isTextDigitAt("seven", line, i)) {
                return 7;
            }
            if (isTextDigitAt("eight", line, i)) {
                return 8;
            }
        } catch (StringIndexOutOfBoundsException ex) { /* ignore */}
        return -1;
    }

    private static boolean isTextDigitAt(String searchedDigit, String line, int i) {
        return searchedDigit.equals(line.substring(i, i + searchedDigit.length()));
    }
}

package advent.day5;

import java.io.IOException;

public class IfYouGiveASeedAFertilizer {

    public static void main(String[] args) throws IOException {
//        Almanac almanac = Parser.parse("src/main/resources/day5/example-part1.txt");
        Almanac almanac = Parser.parse("src/main/resources/day5/my-input.txt");

        System.out.println("Lowest location " + almanac.process());
        //806029445 - part1

    }
}

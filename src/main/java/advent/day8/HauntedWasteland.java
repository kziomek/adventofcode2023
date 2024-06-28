package advent.day8;

import java.io.IOException;
import java.util.List;

public class HauntedWasteland {

    public static void main(String[] args) throws IOException {

        //        Input input = Parser.parse("src/main/resources/day8/example1.txt");
        //        Input input = Parser.parse("src/main/resources/day8/example2.txt");
        //        Input input = Parser.parse("src/main/resources/day8/my-input.txt");
        //                Input input = Parser.parse("src/main/resources/day8/example-part2.txt");
        Input input = Parser.parse("src/main/resources/day8/my-input.txt");

        long steps = runPart1(input.getStarts().get(0), input.getInstructions());
        System.out.println("Steps: " + steps);

        runPart2(input);
    }

    private static void runPart2(Input input) {
        List<Node> starts = input.getStarts();
        String instructions = input.getInstructions();

        List<Node> currentNodes = starts;
        System.out.println("Start nodes: " + currentNodes);

        List<Long> ends = currentNodes.stream().map(node -> runPart1(node, instructions)).toList();

        for (Long end : ends) {
            LCM.printPrimaFactors(end);
        }

        /*
        In my example prime factors for each of results are:
        Prime factors of number 18023 ->  67 269
        Prime factors of number 19637 ->  73 269
        Prime factors of number 11567 ->  43 269
        Prime factors of number 16409 ->  61 269
        Prime factors of number 14257 ->  53 269
        Prime factors of number 21251 ->  79 269

        Least Common Multiplier for above is 269 * 67 * 73 * 43 * 61 * 53 * 79 = 14449445933179

         */

    }

    private static long runPart1(Node start, String instructions) {

        int steps = 0;

        Node currentNode = start;

        while (!currentNode.getValue().endsWith("Z")) {
            char instr = instructions.charAt(steps % instructions.length());
            if (instr == 'L') {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
            steps++;
        }
        return steps;
    }
}

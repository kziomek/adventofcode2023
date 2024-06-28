package advent.day8;

import java.io.IOException;

public class HauntedWasteland {

    public static void main(String[] args) throws IOException {

//        Input input = Parser.parse("src/main/resources/day8/example1.txt");
//        Input input = Parser.parse("src/main/resources/day8/example2.txt");
        Input input = Parser.parse("src/main/resources/day8/my-input.txt");

        int steps = runPart1(input);

        System.out.println("Steps: " + steps);
    }

    private static int runPart1(Input input) {
        Node start = input.getStarts().get(0);
        String instructions = input.getInstructions();

        int steps = 0;

        Node currentNode = start;

        while (!"ZZZ".equals(currentNode.getValue())) {
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

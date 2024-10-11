package advent.day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Aplenty {

    public static void main(String[] args) throws IOException {
        //        List<String> lines = Files.readAllLines(Path.of("src/main/resources/day19/example.txt"));
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/day19/my-input.txt"));

        List<Map<Character, Long>> parts = Parser.parseParts(lines);
        System.out.println("Parts: ");
        System.out.println(parts);

        Map<String, Workflow> workflows = Parser.parseWorkflows(lines);
        System.out.println(workflows);
        System.out.println("Workflows: ");

        long result = process(parts, workflows);
        System.out.println("Total result " + result);
    }

    private static long process(List<Map<Character, Long>> parts, Map<String, Workflow> workflows) {
        System.out.println("Process: ");

        List<Map<Character, Long>> acceptedParts = new ArrayList<>();

        for (Map<Character, Long> part : parts) {
            String result = isAccepted(workflows, part);
            if (result.equals("A")) {
                acceptedParts.add(part);
            }
        }

        System.out.println(acceptedParts);

        return count(acceptedParts);
    }

    private static long count(List<Map<Character, Long>> accepted) {
        return accepted
            .stream()
            .map(el -> el.values()
                .stream()
                .reduce(0L, Long::sum))
            .reduce(0L, Long::sum);
    }

    private static String isAccepted(Map<String, Workflow> workflows, Map<Character, Long> part) {
        String result = "in";
        while (!(result.equals("A") || result.equals("R"))) {
            Workflow workflow = workflows.get(result);
            result = workflow.process(part);
            System.out.println(result);
        }
        return result;
    }
}

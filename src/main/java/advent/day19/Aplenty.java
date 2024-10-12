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

        long result = processPart1(parts, workflows);
        System.out.println("Total result " + result);

        Node aNode = Graph.buildGraph(workflows);
        System.out.println("");

        long total = 0;
        for (Edge prev : aNode.prevs) {
            Map<Character, Range> intersected = Graph.initRanges();
            Edge current = prev;
            do {
                intersect(intersected, current.ranges);
                if (!current.to.prevs.isEmpty()) {
                    current = current.to.prevs.get(0);
                } else {
                    current = null;
                }
            } while (current != null);
            System.out.println(intersected);
            total += countIntersected(intersected);
        }
        System.out.println("Total " + total);
    }

    private static long countIntersected(Map<Character, Range> intersected) {
        return intersected.get('a').getRangeValue() * intersected.get('x').getRangeValue() * intersected.get('s').getRangeValue() * intersected.get('m').getRangeValue();
    }

    private static void intersect(Map<Character, Range> intersected, Map<Character, Range> ranges) {
        for (Character c : intersected.keySet()) {
            Range intersectedRange = intersected.get(c);
            Range range = ranges.get(c);
            intersectedRange.subtract(range);
        }
    }

    private static long processPart1(List<Map<Character, Long>> parts, Map<String, Workflow> workflows) {
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

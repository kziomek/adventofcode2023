package advent.day19;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    public static Node buildGraph(Map<String, Workflow> workflows) {
        Map<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node("A"));
        nodes.put("R", new Node("R"));

        for (String key : workflows.keySet()) {
            Node node = new Node(key);
            nodes.put(key, node);
        }

        for (String key : workflows.keySet()) {
            Node fromNode = nodes.get(key);
            addEdges(nodes, fromNode, workflows.get(key));
        }

        for (Node node : nodes.values()) {
            if (node.prevs.size() > 1) {
                System.out.println("Node " + node.key + " has prevs: " + node.prevs.size());
            }
        }

        return nodes.get("A");
    }

    private static void addEdges(Map<String, Node> nodes, Node fromNode, Workflow workflow) {
        Map<Character, Range> availableRanges = initRanges();

        for (Operation operation : workflow.operations) {
            Node toNode = nodes.get(operation.targetWorkflow);

            Map<Character, Range> operationRanges = extractRanges(availableRanges, operation);
            toNode.prevs.add(new Edge(fromNode, operationRanges));
        }
    }

    private static Map<Character, Range> extractRanges(Map<Character, Range> availableRanges, Operation operation) {
        if (operation.isDummy()) {
            return availableRanges;
        }
        Map<Character, Range> operationRanges = clone(availableRanges);
        transferRange(availableRanges.get(operation.rating), operationRanges.get(operation.rating), operation.oper, operation.value);
        return operationRanges;
    }

    private static void transferRange(Range source, Range target, char oper, long value) {
        if (oper == '<') {
            target.update(target.from, value - 1);
            source.update(value, source.to);
        } else if (oper == '>') {
            target.update(value + 1, target.to);
            source.update(source.from, value);
        }
    }

    private static Map<Character, Range> clone(Map<Character, Range> availableRanges) {
        Map<Character, Range> cloned = new HashMap<>();
        for (Map.Entry<Character, Range> entry : availableRanges.entrySet()) {
            cloned.put(entry.getKey(), entry.getValue().clone());
        }
        return cloned;
    }

    public static Map<Character, Range> initRanges() {
        Map<Character, Range> initialRanges = new HashMap<>();
        initialRanges.put('x', new Range());
        initialRanges.put('m', new Range());
        initialRanges.put('a', new Range());
        initialRanges.put('s', new Range());
        return initialRanges;
    }
}

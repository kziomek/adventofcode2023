package advent.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
    public static List<Map<Character, Long>> parseParts(List<String> lines) {
        return lines.stream()
            .filter(line -> line.startsWith("{"))
            .map(line -> line.substring(1, line.length() - 1))
            .map((line -> Arrays.stream(line.split(","))
                .collect(Collectors.toMap(i -> i.split(":")[0].charAt(0), i -> Long.valueOf(i.split("=")[1])))))
            .toList();
    }

    public static Map<String, Workflow> parseWorkflows(List<String> lines) {
        return lines
            .stream()
            .filter(line -> !line.isEmpty() && Character.isAlphabetic(line.charAt(0)))
            .collect(Collectors.toMap(Parser::parserWorkflowKey, Parser::parseWorkflow));
    }

    private static String parserWorkflowKey(String line) {
        return line.split("\\{")[0];
    }

    private static Workflow parseWorkflow(String line) {
        String[] operationArr = line.substring(line.indexOf("{") + 1, line.indexOf("}"))
            .split(",");

        List<Operation> operations = new ArrayList<>();
        for (int i = 0; i < operationArr.length - 1; i++) {
            operations.add(parseOperation(operationArr[i]));
        }

        operations.add(new Operation('z', 'z', 0L, operationArr[operationArr.length - 1]));
        return new Workflow(operations);
    }

    private static Operation parseOperation(String operationString) {
        char rating = operationString.charAt(0);
        char oper = operationString.charAt(1);
        Long value = Long.valueOf(operationString.substring(2).split(":")[0]);
        String targetWorkflow = operationString.substring(2).split(":")[1];

        return new Operation(rating, oper, value, targetWorkflow);
    }
}

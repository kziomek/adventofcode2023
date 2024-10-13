package advent.day20;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class PulsePropagation {

    public static void main(String[] args) throws IOException {
//        Map<String, Module> modules = Parser.parse("src/main/resources/day20/simple-example.txt");
        Map<String, Module> modules = Parser.parse("src/main/resources/day20/my-example.txt");

        System.out.println(modules);

        initConjunctionModules(modules);

        long highCount = 0;
        long lowCount = 0;

        Queue<Pulse> queue = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            queue.add(new Pulse("button", "broadcaster", false));
            while (!queue.isEmpty()) {
                Pulse pulse = queue.poll();
                System.out.println(pulse);
                if (pulse.isHighPulse) {
                    highCount++;
                } else {
                    lowCount++;
                }
                Module targetModule = modules.get(pulse.target);
                if (targetModule == null) {
                    continue;
                }
                queue.addAll(targetModule.apply(pulse));
            }
        }
        System.out.println("High count " + highCount);
        System.out.println("Low count " + lowCount);
        System.out.println("Result: " + highCount * lowCount);
    }

    private static void initConjunctionModules(Map<String, Module> modules) {
        List<Module> conjunctionModules = modules.values().stream().filter(module -> module instanceof ConjunctionModule).toList();
        for (Module conjunctionModule : conjunctionModules) {
            Map<String, Boolean> inputs = modules.values().stream().filter(module -> module.targets.contains(conjunctionModule.name)).collect(Collectors.toMap(module -> module.name, module -> false));
            ((ConjunctionModule) conjunctionModule).updateInputs(inputs);
        }
    }
}

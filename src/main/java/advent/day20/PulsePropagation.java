package advent.day20;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
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

        Map<String, Long> soughtIterationWithLowPulse = new HashMap<>();
        Set<String> probedModules = Set.of("mk", "fp", "xt", "zc");

        for (int i = 1; i < 100000; i++) {
            System.out.println("Press " + i);
            queue.add(new Pulse("button", "broadcaster", false));
            while (!queue.isEmpty()) {
                Pulse pulse = queue.poll();
                System.out.println(pulse);
                if (pulse.isHighPulse) {
                    highCount++;
                } else {
                    lowCount++;
                }

                //                if (pulse.target.equals("rx") && !pulse.isHighPulse) {
                if (probedModules.contains(pulse.target) && !pulse.isHighPulse) {
                    if (soughtIterationWithLowPulse.get(pulse.target) == null) {
                        soughtIterationWithLowPulse.put(pulse.target, (long) i);
                    }
                    if (soughtIterationWithLowPulse.size() == probedModules.size()) {
                        System.out.println(soughtIterationWithLowPulse);
                        Long part2Result = soughtIterationWithLowPulse.values().stream().reduce(1L, (a, b) -> a * b);
                        System.out.println("Part 2 result " + part2Result);
                        System.exit(0);
                    }

                    System.out.println("Number of button presses " + (i));
                    break;
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

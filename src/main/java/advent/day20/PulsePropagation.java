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

    /**
     * In part2 graphs are mostly independent. They join at &kl module, so we can calculate minimum number of pressing button for each of modules pulsing to &kl.
     * Then we calculate GDC which is strait forward multiply of numbers in this case.
     */
    public static void main(String[] args) throws IOException {
        //        Map<String, Module> modules = Parser.parse("src/main/resources/day20/simple-example.txt");
        Map<String, Module> modules = Parser.parse("src/main/resources/day20/my-example.txt");

        System.out.println(modules);

        initConjunctionModules(modules);

        Queue<Pulse> queue = new LinkedList<>();

        Map<String, Long> soughtIterationWithLowPulse = new HashMap<>();
        Set<String> probedModules = Set.of("mk", "fp", "xt", "zc");

        long pressNumber = 0;
        while (true) {
            pressNumber++;
            queue.add(new Pulse("button", "broadcaster", false));
            while (!queue.isEmpty()) {
                Pulse pulse = queue.poll();
                if (probedModules.contains(pulse.target) && !pulse.isHighPulse) {
                    if (soughtIterationWithLowPulse.get(pulse.target) == null) {
                        soughtIterationWithLowPulse.put(pulse.target, pressNumber);
                    }
                    if (soughtIterationWithLowPulse.size() == probedModules.size()) {
                        System.out.println(soughtIterationWithLowPulse);
                        Long part2Result = soughtIterationWithLowPulse.values().stream().reduce(1L, (a, b) -> a * b);
                        System.out.println("Part 2 result " + part2Result);
                        System.exit(0);
                    }
                    break;
                }
                Module targetModule = modules.get(pulse.target);
                if (targetModule == null) {
                    continue;
                }
                queue.addAll(targetModule.apply(pulse));
            }
        }
    }

    private static void initConjunctionModules(Map<String, Module> modules) {
        List<Module> conjunctionModules = modules.values().stream().filter(module -> module instanceof ConjunctionModule).toList();
        for (Module conjunctionModule : conjunctionModules) {
            Map<String, Boolean> inputs = modules.values().stream().filter(module -> module.targets.contains(conjunctionModule.name)).collect(Collectors.toMap(module -> module.name, module -> false));
            ((ConjunctionModule) conjunctionModule).updateInputs(inputs);
        }
    }
}

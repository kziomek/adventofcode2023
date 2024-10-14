package advent.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConjunctionModule extends Module {

    Map<String, Boolean> inputs = new HashMap<>();

    public ConjunctionModule(String name, List<String> targets) {
        super(name, targets);
    }

    @Override
    public List<Pulse> apply(Pulse pulse) {
        inputs.put(pulse.source, pulse.isHighPulse);
        System.out.println(name + " trues " + inputs.values().stream().filter(v -> v.equals(true)).count());
        boolean shouldProducedHighPulse = inputs.containsValue(false);
        if (!shouldProducedHighPulse) {
            System.out.println("conj " + name);
            System.out.println(pulse.source + " pulsing low to " + name);
            System.out.print("");
        }
        return targets.stream().map(target -> new Pulse(pulse.target, target, shouldProducedHighPulse)).toList();
    }

    @Override
    public String toString() {
        return "ConjunctionModule{" +
            "name='" + name + '\'' +
            ", targets=" + targets +
            '}';
    }

    public void updateInputs(Map<String, Boolean> inputs) {
        this.inputs = inputs;
    }
}

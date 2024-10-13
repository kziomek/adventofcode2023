package advent.day20;

import java.util.List;

public class FlipFlopModule extends  Module {

    boolean isOn = false;

    public FlipFlopModule(String name, List<String> targets) {
        super(name, targets);
    }

    @Override
    public List<Pulse> apply(Pulse pulse) {
        if (pulse.isHighPulse) {
            return List.of();
        } else {
            isOn = !isOn;
            return targets.stream().map( target -> new Pulse(pulse.target, target, isOn)).toList();
        }
    }

    @Override
    public String toString() {
        return "FlipFlopModule{" +
            "targets=" + targets +
            ", name='" + name + '\'' +
            '}';
    }
}

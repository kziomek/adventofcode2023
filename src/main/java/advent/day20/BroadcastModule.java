package advent.day20;

import java.util.List;

public class BroadcastModule extends Module {

    public BroadcastModule(String name, List<String> targets) {
        super(name, targets);
    }

    @Override
    public List<Pulse> apply(Pulse pulse) {
        return targets.stream().map(target -> new Pulse(pulse.target, target, pulse.isHighPulse)).toList();
    }

    @Override
    public String toString() {
        return "BroadcastModule{" +
            "name='" + name + '\'' +
            ", targets=" + targets +
            '}';
    }
}

package advent.day20;

import java.util.List;

public abstract class Module {

    String name;
    List<String> targets;

    public Module(String name, List<String> targets) {
        this.name = name;
        this.targets = targets;
    }

    //    public abstract List<Pulse> apply();

    @Override
    public String toString() {
        return "Module{" +
            "name='" + name + '\'' +
            ", targets=" + targets +
            '}';
    }

    public abstract List<Pulse> apply(Pulse pulse);
}

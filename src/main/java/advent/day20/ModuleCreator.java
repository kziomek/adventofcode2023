package advent.day20;

import java.util.List;

public class ModuleCreator {

    public static Module createModule(String moduleString, List<String> targets){
        if (moduleString.equals("broadcaster")) {
            return new BroadcastModule(moduleString, targets);
        }
        if (moduleString.startsWith("%")) {
            return new FlipFlopModule(moduleString.substring(1), targets);
        }
        if (moduleString.startsWith("&")) {
            return new ConjunctionModule(moduleString.substring(1), targets);
        }
        throw new IllegalStateException();
    }
}

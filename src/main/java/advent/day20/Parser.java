package advent.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static Map<String, Module> parse(String path) throws IOException {
        Map<String, Module> modules = new HashMap<>();
        for (String line : Files.readAllLines(Path.of(path))) {
            String[] splitLine = line.split(" -> ");
            String moduleString = splitLine[0];
            List<String> targetModules = List.of(splitLine[1].split(", "));
            Module module = ModuleCreator.createModule(moduleString, targetModules);
            modules.put(module.name, module);
        }
        return modules;
    }
}

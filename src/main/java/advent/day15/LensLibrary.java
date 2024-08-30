package advent.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class LensLibrary {

    public static void main(String[] args) throws IOException {
        long hash = calculateHash("HASH");
        System.out.println(hash);

        String initialisationSequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
//        long exampleResult = calculateInitialisationSequence(initialisationSequence);
        long exampleResult = calculatePart2Sequence(initialisationSequence);
        System.out.println(exampleResult);

        String myInitialisationSequence = Files.readAllLines(Path.of("src/main/resources/day15/my-input.txt")).get(0);
        System.out.println(myInitialisationSequence);
        long myExampleResult = calculatePart2Sequence(myInitialisationSequence);
        System.out.println(myExampleResult);
    }

    private static long calculatePart2Sequence(String initialisationSequence) {
        LensHashMap map = new LensHashMap();

        String[] steps = initialisationSequence.split(",");
        for (String step : steps) {
            if (step.contains("=")){
                String[] splitStep = step.split("=");
                String label = splitStep[0];
                String size = splitStep[1];
                map.put(new Len(label, Integer.parseInt(size)));

            }
            if (step.contains("-")){
                String[] splitStep = step.split("-");
                String label = splitStep[0];
                map.delete(label);
            }
        }

        return map.focusingPower();
    }

    public static long calculateInitialisationSequence(String initialisationSequence) {
        return Arrays.stream(initialisationSequence.split(","))
            .map(LensLibrary::calculateHash)
            .reduce(0L, Long::sum);
    }

    public static long calculateHash(String s) {
        return s.chars()
            .reduce(0, (c, i) -> 17 * (c + i) % 256);
    }
}

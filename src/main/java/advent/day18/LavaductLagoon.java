package advent.day18;

import java.io.IOException;
import java.util.List;

public class LavaductLagoon {

    public static void main(String[] args) throws IOException {

        boolean isPart2 = true;

//        List<Step> steps = Parser.parse("src/main/resources/day18/example.txt", isPart2);
                                        List<Step> steps = Parser.parse("src/main/resources/day18/my-input.txt", isPart2);

        long area = SweepLineAlgorithm.calculateArea(steps);
        System.out.println("Area: " + area);

//        long areaFromGraph = GraphAlgorithm.calculateArea(steps);
//        System.out.println("Area from graph alg: " + areaFromGraph);
    }

}

package advent.day18;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class LavaductLagoon {

    // part 2
    // 1 collect lines
    // collect areas from lines
    // calculate total area of squares
    public static void main(String[] args) throws IOException {

//        List<Step> steps = Parser.parse("src/main/resources/day18/example.txt");
//        List<Step> steps = Parser.parse("src/main/resources/day18/example.txt");
                                List<Step> steps = Parser.parse("src/main/resources/day18/my-input.txt");


        long area = SweepLineAlgorithm.calculateArea(steps);
        System.out.println("Area: " + area);
//        part1(steps);
    }

    private static void part1(List<Step> steps) {
        char[][] lagoon = buildLagoon(steps);
        digLagoon(steps, lagoon);
        print(lagoon);
        System.out.println();

        fillLagoonWithGraph(lagoon);

        print(lagoon);
        System.out.println();
        //        lagoon = fillLagoon(lagoon);
        int count = countCubicMeters(lagoon);

        //        print(lagoon);
        System.out.println("Size: " + count);
    }

    private static void fillLagoonWithGraph(char[][] lagoon) {

        Set<Pos> visited = new HashSet<>();
        Queue<Pos> queue = new LinkedList<>();

        Pos start = new Pos(0, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            if (visited.contains(pos)) {
                continue;
            }
            lagoon[pos.getI()][pos.getJ()] = ',';
            visited.add(pos);

            addPoints(lagoon, pos, queue);
        }

        for (int i = 0; i < lagoon.length; i++) {
            for (int j = 0; j < lagoon[0].length; j++) {
                if (lagoon[i][j] == '.') {
                    lagoon[i][j] = '#';
                }
            }
        }
    }

    private static void addPoints(char[][] lagoon, Pos pos, Queue<Pos> queue) {
        //U
        if (pos.getI() > 0 && lagoon[pos.getI() - 1][pos.getJ()] == '.') {
            queue.add(new Pos(pos.getI() - 1, pos.getJ()));
        }
        //D
        if (pos.getI() < lagoon.length - 1 && lagoon[pos.getI() + 1][pos.getJ()] == '.') {
            queue.add(new Pos(pos.getI() + 1, pos.getJ()));
        }
        //L
        if (pos.getJ() > 0 && lagoon[pos.getI()][pos.getJ() - 1] == '.') {
            queue.add(new Pos(pos.getI(), pos.getJ() - 1));
        }
        //R
        if (pos.getJ() < lagoon[0].length - 1 && lagoon[pos.getI()][pos.getJ() + 1] == '.') {
            queue.add(new Pos(pos.getI(), pos.getJ() + 1));
        }
    }

    private static int countCubicMeters(char[][] lagoon) {
        int count = 0;
        for (char[] row : lagoon) {
            for (char c : row) {
                if (c == '#') {
                    count++;
                }
            }
        }

        return count;
    }

    private static char[][] fillLagoon(char[][] lagoon) {
        char[][] fullLagoon = new char[lagoon.length][lagoon[0].length];
        boolean in = false;
        for (int i = 0; i < lagoon.length; i++) {
            for (int j = 0; j < lagoon[0].length; j++) {

                if (lagoon[i][j] == '#' && lagoon[i][j - 1] == '.') {
                    in = !in;
                }
                if (lagoon[i][j] == '.' && in) {
                    fullLagoon[i][j] = '#';
                }
                if (lagoon[i][j] == '.' && !in) {
                    fullLagoon[i][j] = '.';
                }
                if (lagoon[i][j] == '#') {
                    fullLagoon[i][j] = '#';
                }
            }
            //            in = false;
        }
        return fullLagoon;
    }

    private static void digLagoon(List<Step> steps, char[][] lagoon) {
        int x = lagoon[0].length / 2;
        int y = lagoon.length / 2;
        lagoon[y][x] = '#';

        for (Step step : steps) {
            if (step.getDirection() == 'R') {
                for (int i = 1; i <= step.getLength(); i++) {
                    x++;
                    lagoon[y][x] = '#';
                }
            }
            if (step.getDirection() == 'L') {
                for (int i = 1; i <= step.getLength(); i++) {
                    x--;
                    lagoon[y][x] = '#';
                }
            }
            if (step.getDirection() == 'U') {
                for (int i = 1; i <= step.getLength(); i++) {
                    y--;
                    lagoon[y][x] = '#';
                }
            }
            if (step.getDirection() == 'D') {
                for (int i = 1; i <= step.getLength(); i++) {
                    y++;
                    lagoon[y][x] = '#';
                }
            }
        }
    }

    private static void print(char[][] lagoon) {
        for (char[] line : lagoon) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static char[][] buildLagoon(List<Step> steps) {
        //start 0 0
        int maxOx = 0, maxOy = 0;
        int ox = 0, oy = 0;

        for (Step step : steps) {
            if (step.getDirection() == 'U') {
                oy += step.getLength();
                if (oy > maxOy) {
                    maxOy = oy;
                }
            }
            if (step.getDirection() == 'D') {
                oy -= step.getLength();
                if (Math.abs(oy) > maxOy) {
                    maxOy = Math.abs(oy);
                }
            }
            if (step.getDirection() == 'R') {
                ox += step.getLength();
                if (ox > maxOx) {
                    maxOx = ox;
                }
            }
            if (step.getDirection() == 'L') {
                ox -= step.getLength();
                if (Math.abs(ox) > maxOx) {
                    maxOx = Math.abs(ox);
                }
            }
        }
        System.out.println("maxOx " + maxOx);
        System.out.println("maxOy " + maxOy);

        char[][] lagoon = new char[2 * maxOy + 3][2 * maxOx + 3];
        for (int i = 0; i < lagoon.length; i++) {
            for (int j = 0; j < lagoon[0].length; j++) {
                lagoon[i][j] = '.';
            }
        }
        return lagoon;
    }
}

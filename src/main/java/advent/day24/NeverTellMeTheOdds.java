package advent.day24;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class NeverTellMeTheOdds {

    //    static final long MIN = 7L;
    //    static final long MAX = 25L;

    static final long MIN = 200000000000000L;
    static final long MAX = 400000000000000L;

    private static final int N = 10;

    public static void main(String[] args) throws IOException {

        //        List<Input> lines = Parser.parse("src/main/resources/day24/example.txt");
        //                        List<Input> lines = Parser.parse("src/main/resources/day24/my-input.txt");
        List<Input> lines = Parser.parse("src/main/resources/day24/my-input-short.txt");

        for (Input line : lines) {

            System.out.println(line);
        }

                /*
        long counter = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Input line1 = lines.get(i);
                Input line2 = lines.get(j);
                System.out.println(line1);
                System.out.println(line2);

                if (isParallel(line1, line2)) {
                    System.out.println("Hailstones' paths are parallel; they never intersect.");
                    System.out.println();
                    continue;
                }

                Intersection intersection = intersect(line1, line2);
                System.out.println(intersection);

                if (isInCrossArea(intersection)) {
                    System.out.println("Is in crossed area");
                    if (isFutureIntersection(intersection, line1) && isFutureIntersection(intersection, line2)) {
                        System.out.println("Is future intersection");
                        counter++;
                    }
                }
                System.out.println();
            }
            System.out.println("Result " + counter);
        }*/

        // part 2
        part2(lines);
    }

    private static void part2(List<Input> lines) {
        Input line0 = lines.get(0);
        //        Input line1 = lines.get(1);

        for (int v = -1 * N; v < N; v++) {
            for (int t = 0; t < N; t++) {
                long x = line0.px + t * (line0.vx - v);
                System.out.println("x=" + x + " vx=" + v + " t=" + t);
                if (validateLines(lines, v, x)) {
                    System.out.println("x=" + x + " vx=" + v + " t=" + t);
                }
            }
        }

        //        for (int v = -1 * N; v < N; v++) {
        //            for (int t = 0; t < N; t++) {
        //                long y = line0.py + t * (line0.vy - v);
        //                //                                System.out.println("y=" + y + " vy=" + v + " t=" + t);
        //                if (validateLineY(lines, v, y)) {
        //                    System.out.println("y=" + y + " vy=" + v + " t=" + t);
        //                }
        //            }
        //        }
        //
        //        for (int v = -1 * N; v < N; v++) {
        //            for (int t = 0; t < N; t++) {
        //                long z = line0.pz + t * (line0.vz - v);
        //                //                                System.out.println("z=" + z + " vz=" + v + " t=" + t);
        //                if (validateLineZ(lines, v, z)) {
        //                    System.out.println("z=" + z + " vz=" + v + " t=" + t);
        //                }
        //            }
        //        }
    }

    private static boolean validateLines(List<Input> lines, int v, long ox) {
        for (Input line : lines) {
            if (!valid(line, v, ox)) {
                return false;
            }
        }
        return true;
    }

    private static boolean valid(Input line, int v, long ox) {
        boolean valid = false;
        for (int t = 1; t < N; t++) {
            long x = line.px + t * (line.vx - v);
            System.out.println("  " + line + "  x=" + x + " vx=" + v + " t=" + t);
            if (x == ox) {
                valid = true;
                //                System.out.println("t= " + t);
                break;
            }
        }
        return valid;
    }

    private static boolean validateLineY(List<Input> lines, int v, long oy) {
        for (Input line : lines) {
            if (!validY(line, v, oy)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validY(Input line, int v, long oy) {
        boolean valid = false;
        for (int t = 1; t < N; t++) {
            long y = line.py + t * (line.vy - v);
            if (y == oy) {
                valid = true;
                //                System.out.println("t= " + t);
                break;
            }
        }
        return valid;
    }

    private static boolean validateLineZ(List<Input> lines, int v, long oz) {
        for (Input line : lines) {
            if (!validZ(line, v, oz)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validZ(Input line, int v, long oz) {
        boolean valid = false;
        for (int t = 1; t < N; t++) {
            long z = line.pz + t * (line.vz - v);
            if (z == oz) {
                valid = true;
                //                System.out.println("t= " + t);
                break;
            }
        }
        return valid;
    }

    private static Intersection intersect(Input line1, Input line2) {
        double x = (line2.b() - line1.b()) / (line1.a() - line2.a());
        double y1 = line1.a() * x + line1.b();
        double y2 = line2.a() * x + line2.b();
        return new Intersection(x, y1);
    }

    private static boolean isParallel(Input line1, Input line2) {
        return line1.a() == line2.a();
    }

    private static boolean isInCrossArea(Intersection intersection) {
        return (intersection.x >= MIN && intersection.x <= MAX
            && intersection.y >= MIN && intersection.y <= MAX);
    }

    private static boolean isFutureIntersection(Intersection intersection, Input line) {
        return (intersection.x - line.px) / line.vx >= 0;
    }
}

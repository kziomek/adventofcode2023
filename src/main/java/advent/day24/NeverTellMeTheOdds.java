package advent.day24;

import java.io.IOException;
import java.util.List;

public class NeverTellMeTheOdds {

    //    static final long MIN = 7L;
    //    static final long MAX = 25L;

    static final long MIN = 200000000000000L;
    static final long MAX = 400000000000000L;

    private static final int N = 10;

    public static void main(String[] args) throws IOException {

        List<Input> lines = Parser.parse("src/main/resources/day24/example.txt");
        //                        List<Input> lines = Parser.parse("src/main/resources/day24/my-input.txt");
        //        List<Input> lines = Parser.parse("src/main/resources/day24/my-input-short.txt");

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

        for (int v = -3; v < -2; v++) {
            System.out.println();
            for (int t = 5; t < 6; t++) {
                long xr = line0.px + t * (line0.vx - v);
                if (validateLinesX(lines, v, xr)) {
                    System.out.println("xr=" + xr + " vx=" + v + " t=" + t);
                    System.out.println();
                }
            }
        }

        for (int v = 1; v < 2; v++) {
            for (int t = 5; t < 6; t++) {
                long yr = line0.py + t * (line0.vy - v);
                if (validateLineY(lines, v, yr)) {
                    System.out.println("yr=" + yr + " vy=" + v + " t=" + t);
                    System.out.println();
                }
            }
        }
        //
        for (int v = 2; v < 3; v++) {
            for (int t = 5; t < 6; t++) {
                long zr = line0.pz + t * (line0.vz - v);
                if (validateLineZ(lines, v, zr)) {
                    System.out.println("zr=" + zr + " vz=" + v + " t=" + t);
                    System.out.println();
                }
            }
        }
    }

    private static boolean validateLinesX(List<Input> lines, int v, long ox) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validX(lines.get(i), v, ox)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validX(Input line, int vr, long xr) {
        double t = (double) (xr - line.px) / (line.vx - vr);
        System.out.println("valid t " + t);
        return t % 1 == 0;
    }

    private static boolean validateLineY(List<Input> lines, int v, long oy) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validY(lines.get(i), v, oy)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validY(Input line, int vr, long yr) {
        double t = (double) (yr - line.py) / (line.vy - vr);
        System.out.println("valid t " + t);
        return t % 1 == 0;
    }

    private static boolean validateLineZ(List<Input> lines, int v, long oz) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validZ(lines.get(i), v, oz)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validZ(Input line, int vr, long zr) {
        double t = (double) (zr - line.pz) / (line.vz - vr);
        System.out.println("valid t " + t);
        return t % 1 == 0;
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

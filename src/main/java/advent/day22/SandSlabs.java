package advent.day22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


// TODO try to print settled slabs in X and Y orientations to debug
// TODO Debug around slabs spanning across oz
public class SandSlabs {
    public static void main(String[] args) throws IOException {
//                List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/example.txt"));
        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/my-input.txt"));
        //                List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/small-input.txt"));

        bricks = new ArrayList<>(bricks); // mutable

        validateOrientation(bricks);

//        bricks.sort(Comparator.comparingInt(brick -> brick.a.z));
        System.out.println("Bricks");
        System.out.println(bricks);
        //sort by z

        System.out.println("Settled bricks");
        List<Brick> settledBricks = settleBricks(bricks);
        System.out.println(settledBricks);

        int result = countDesintegrationCandidates(settledBricks);
        System.out.println("Part1 result " + result);
    }

    private static void validateOrientation(List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (brick.a.x > brick.b.x || brick.a.y > brick.b.y || brick.a.z > brick.b.z) {
                throw new IllegalStateException("bricks are not ordered as assumed");
            }
        }
    }

    private static int countDesintegrationCandidates(List<Brick> settledBricks) {
        // group list by a.z
        Map<Integer, List<Brick>> zMapA = new HashMap<>();
        for (Brick settledBrick : settledBricks) {
            zMapA.computeIfAbsent(settledBrick.a.z, k -> new ArrayList<>());
            zMapA.get(settledBrick.a.z).add(settledBrick);
        }
        Map<Integer, List<Brick>> zMapB = new HashMap<>();
        for (Brick settledBrick : settledBricks) {
            zMapB.computeIfAbsent(settledBrick.b.z, k -> new ArrayList<>());
            zMapB.get(settledBrick.b.z).add(settledBrick);
        }

        Set<Brick> disintegrationCandidates = new HashSet<>();
        for (Brick supportingBrick : settledBricks) {
            List<Brick> supportedBricks = findSupported(supportingBrick, zMapA.get(supportingBrick.b.z + 1));
            System.out.println("Brick " + supportingBrick + " supports " + supportedBricks);
            boolean allSupportedAlsoByAnotherBrick = true;
            for (Brick supportedBrick : supportedBricks) {
                if (hasAnotherSupport(supportedBrick, supportingBrick, zMapB.get(supportedBrick.a.z - 1))) {
                    System.out.println("  Brick " + supportedBrick + " has another support");
                } else {
                    allSupportedAlsoByAnotherBrick = false;
                }
            }
            if (allSupportedAlsoByAnotherBrick) {
                disintegrationCandidates.add(supportingBrick);
            }
        }

        // for each brick
        // get supported bricks
        // count supported brick with yet another support

        System.out.println("Disintegration candidates");
        System.out.println(disintegrationCandidates);
        return disintegrationCandidates.size();
    }

    private static List<Brick> findSupported(Brick brick, List<Brick> candidateBricks) {
        if (candidateBricks == null) {
            return new ArrayList<>();
        }
        return candidateBricks.stream().filter(cb -> cb.intersectsXY(brick)).toList();
    }

    private static boolean hasAnotherSupport(Brick supportedBrick, Brick supportingBrick, List<Brick> bricks) {
        List<Brick> otherSupporters = bricks
            .stream()
            .filter(cb -> !cb.equals(supportingBrick))
            .filter(cb -> cb.intersectsXY(supportedBrick)).toList();
        return !otherSupporters.isEmpty();
    }

    private static List<Brick> settleBricks(List<Brick> bricks) {
        List<Brick> settledBricks = new ArrayList<>();
        int maxX = bricks.stream().map(brick -> brick.b.x).max(Integer::compareTo).get();
        int maxY = bricks.stream().map(brick -> brick.b.y).max(Integer::compareTo).get();

        System.out.println(maxX);
        System.out.println(maxY);

        int[][] tops = new int[maxX + 1][maxY + 1];

        for (Brick brick : bricks) {
            int settleZ = findSettleHeight(brick, tops);
            int diff = brick.a.z - settleZ;
            Brick settledBrick = new Brick(new Point(brick.a.x, brick.a.y, brick.a.z - diff), new Point(brick.b.x, brick.b.y, brick.b.z - diff));
            updateTops(settledBrick, tops);
            settledBricks.add(settledBrick);
        }

        return settledBricks;
    }

    private static void updateTops(Brick brick, int[][] tops) {
        for (int x = brick.a.x; x <= brick.b.x; x++) {
            tops[x][brick.a.y] = brick.a.z;
        }
        for (int y = brick.a.y; y <= brick.b.y; y++) {
            tops[brick.a.x][y] = brick.a.z;
        }
    }

    private static int findSettleHeight(Brick brick, int[][] tops) {
        int minSettleHeight = 0;
        // x
        for (int x = brick.a.x; x <= brick.b.x; x++) {
            minSettleHeight = Math.max(minSettleHeight, tops[x][brick.a.y]);
        }
        for (int y = brick.a.y; y <= brick.b.y; y++) {
            minSettleHeight = Math.max(minSettleHeight, tops[brick.a.x][y]);
        }

        // y
        return minSettleHeight + 1;
    }
}

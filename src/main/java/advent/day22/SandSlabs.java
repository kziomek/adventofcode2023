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

/**
 *  Solution should be obviously refactored a bit with supporting unit tests for edge cases, but it's working at this point.
 */
public class SandSlabs {
    public static void main(String[] args) throws IOException {
        //        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/example.txt"));
        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/my-input.txt"));
        //                        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/small-input.txt"));

        bricks = new ArrayList<>(bricks); // mutable

        validateOrientation(bricks);

        bricks.sort(Comparator.comparingInt(brick -> brick.a.z));
        System.out.println("Bricks");
        System.out.println(bricks);
        //sort by z

        System.out.println("Settled bricks");
        List<Brick> settledBricks = settleBricks(bricks);
        System.out.println(settledBricks);

        validateSettledBricksDoNotOverlap(settledBricks);
        validateSettledBricksHaveSupporters(settledBricks);

        connectBricksInGraph(settledBricks);
        int part1Result = solvePart1(settledBricks);
        int part2Result = solvePart2(settledBricks);
        // then

        System.out.println("Part1 result " + part1Result);
        System.out.println("Part2 result " + part2Result);
    }

    private static int solvePart2(List<Brick> settledBricks) {
        System.out.println("Solve Part 2");

        int count = 0;

        for (int i = settledBricks.size() - 1; i >= 0; i--) {
            Brick supportingBrick = settledBricks.get(i);
            System.out.println("supporting brick " + supportingBrick);
            int countFalling = 0;
            Set<Brick> disintegratedBricks = new HashSet<>();
            disintegratedBricks.add(supportingBrick);

            int lastSeenSize = disintegratedBricks.size();
            do {
                for (Brick supportedBrick : supportingBrick.supportedBricks) {
                    lastSeenSize = disintegratedBricks.size();
                    if (!supportedBrick.hasRemainingSupportingBricks(disintegratedBricks)) { // it would collapse, count it and upper bricks
                        System.out.println(supportedBrick + " - no secondary support");
                        countFalling += countFalling(supportedBrick, disintegratedBricks);
                        System.out.println(countFalling + " falling for " + supportingBrick);
                    }
                }
            }
            while (lastSeenSize != disintegratedBricks.size());
            count += countFalling;
        }
        return count;
    }

    private static int countFalling(Brick brick, Set<Brick> disintegratedBricks) {
        if (disintegratedBricks.contains(brick)) {
            return 0;
        }
        if (brick.hasRemainingSupportingBricks(disintegratedBricks)) {
            return 0;
        }
        disintegratedBricks.add(brick);
        int count = 1;
        for (Brick supportedBrick : brick.supportedBricks) {
            count += countFalling(supportedBrick, disintegratedBricks);
        }
        return count;
    }

    private static int solvePart1(List<Brick> settledBricks) {
        int count = 0;
        for (Brick settledBrick : settledBricks) {
            boolean areAllSupportedBySecondaryBrick = true;
            for (Brick supportedBrick : settledBrick.supportedBricks) {
                Set<Brick> lostSupportingBricks = Set.of(settledBrick);
                if (!supportedBrick.hasRemainingSupportingBricks(lostSupportingBricks)) {
                    areAllSupportedBySecondaryBrick = false;
                }
            }
            if (areAllSupportedBySecondaryBrick) {
                count++;
            }
        }

        return count;
    }

    private static void connectBricksInGraph(List<Brick> settledBricks) {
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
        for (Brick supportingBrick : settledBricks) {
            List<Brick> supportedBricks = findSupported(supportingBrick, zMapA.get(supportingBrick.b.z + 1));

            supportingBrick.setSupportedBricks(new HashSet<>(supportedBricks));
            System.out.println("Brick " + supportingBrick + " supports " + supportedBricks);
            for (Brick supportedBrick : supportedBricks) {
                supportedBrick.setSupportingBricks(new HashSet<>(supportingBricks(supportedBrick, zMapB.get(supportedBrick.a.z - 1))));

                if (hasAnotherSupport(supportedBrick, supportingBrick, zMapB.get(supportedBrick.a.z - 1))) {
                    System.out.println("  Brick " + supportedBrick + " has another support");
                    supportedBrick.setHasMoreThanOneSupporingBrick(true);
                } else {
                    supportedBrick.setHasMoreThanOneSupporingBrick(false);
                }
            }
        }
    }

    private static void validateSettledBricksHaveSupporters(List<Brick> settledBricks) {
        Map<Integer, List<Brick>> zMapB = new HashMap<>();
        for (Brick settledBrick : settledBricks) {
            zMapB.computeIfAbsent(settledBrick.b.z, k -> new ArrayList<>());
            zMapB.get(settledBrick.b.z).add(settledBrick);
        }

        for (Brick settledBrick : settledBricks) {
            if (settledBrick.a.z > 1 && !hasAnySupport(settledBrick, zMapB.get(settledBrick.a.z - 1))) {
                System.out.println("  Brick " + settledBrick + " do not have support");
                throw new IllegalStateException("Settled brick do not have any support");
            }
        }
    }

    private static void validateSettledBricksDoNotOverlap(List<Brick> settledBricks) {
        System.out.println("Validate settled bricks");
        for (int i = 0; i < settledBricks.size(); i++) {
            for (int j = 0; j < settledBricks.size(); j++) {
                if (settledBricks.get(i).intersectsXY(settledBricks.get(j))) {
                    if (settledBricks.get(i).intersectsZ(settledBricks.get(j))) {
                        if (!settledBricks.get(i).equals(settledBricks.get(j))) {
                            System.out.println(settledBricks.get(i));
                            System.out.println(settledBricks.get(j));
                            System.out.println("[" + settledBricks.get(i).a.z + " " + settledBricks.get(i).b.z + "][" + settledBricks.get(j).a.z + " " + settledBricks.get(j).b.z + "]");

                            throw new IllegalStateException("settled bricks are overlapping");
                        }
                    }
                }
            }
        }
        System.out.println();
    }

    private static void validateOrientation(List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (brick.a.x > brick.b.x || brick.a.y > brick.b.y || brick.a.z > brick.b.z) {
                throw new IllegalStateException("bricks are not ordered as assumed");
            }
        }
    }

    private static List<Brick> findSupported(Brick brick, List<Brick> candidateBricks) {
        System.out.println("Find supported");
        System.out.println(brick);
        if (candidateBricks == null) {
            return new ArrayList<>();
        }
        char[][] grid = printXY(candidateBricks);
        List<Brick> supported = candidateBricks.stream().filter(cb -> cb.intersectsXY(brick)).toList();

        if (brick.b.z - brick.a.z > 1) {
            if (grid[brick.a.y][brick.a.x] == 'O' && supported.size() != 1) {
                throw new IllegalStateException("something wrong on z brick");
            }
            if (grid[brick.a.y][brick.a.x] == '.' && !supported.isEmpty()) {
                throw new IllegalStateException("something wrong on z brick");
            }
        }
        return supported;
    }

    private static char[][] printXY(List<Brick> candidateBricks) {
        char[][] grid = new char[10][10];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = '.';
            }
        }

        for (Brick candidateBrick : candidateBricks) {
            for (int x = candidateBrick.a.x; x <= candidateBrick.b.x; x++) {
                grid[candidateBrick.a.y][x] = 'O';
            }
            for (int y = candidateBrick.a.y; y <= candidateBrick.b.y; y++) {
                grid[y][candidateBrick.a.x] = 'O';
            }
        }

        for (char[] chars : grid) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        return grid;
    }

    private static boolean hasAnySupport(Brick supportedBrick, List<Brick> bricks) {
        if (bricks == null) {
            return false;
        }
        List<Brick> otherSupporters = bricks
            .stream()
            .filter(cb -> cb.intersectsXY(supportedBrick)).toList();

        return !otherSupporters.isEmpty();
    }

    private static List<Brick> supportingBricks(Brick supportedBrick, List<Brick> lowerBricks) {
        if (lowerBricks == null) {
            return List.of();
        }
        List<Brick> supporters = lowerBricks
            .stream()
            .filter(cb -> cb.intersectsXY(supportedBrick)).toList();

        if (supportedBrick.b.z - supportedBrick.a.z > 1 && supporters.size() != 1) {
            throw new IllegalStateException("z brick should have one supporter only");
        }
        return supporters;
    }

    private static boolean hasAnotherSupport(Brick supportedBrick, Brick supportingBrick, List<Brick> bricks) {
        if (bricks == null) {
            return false;
        }
        List<Brick> otherSupporters = bricks
            .stream()
            .filter(cb -> !cb.equals(supportingBrick))
            .filter(cb -> cb.intersectsXY(supportedBrick)).toList();

        if (supportedBrick.b.z - supportedBrick.a.z > 1 && !otherSupporters.isEmpty()) {
            throw new IllegalStateException("z brick should now have other supporters");
        }
        return !otherSupporters.isEmpty();
    }

    private static List<Brick> settleBricks(List<Brick> bricks) {
        List<Brick> settledBricks = new ArrayList<>();
        int maxX = bricks.stream().map(brick -> brick.b.x).max(Integer::compareTo).get();
        int maxY = bricks.stream().map(brick -> brick.b.y).max(Integer::compareTo).get();

        System.out.println(maxX);
        System.out.println(maxY);

        int[][] tops = new int[maxY + 1][maxX + 1];

        for (Brick brick : bricks) {
            int settleZ = findSettleHeight(brick, tops);
            int diff = brick.a.z - settleZ;
            Brick settledBrick = new Brick(new Point(brick.a.x, brick.a.y, brick.a.z - diff), new Point(brick.b.x, brick.b.y, brick.b.z - diff));
            updateTops(settledBrick, tops);
            settledBricks.add(settledBrick);
            //            validateSettledBricks(settledBricks);
        }

        return settledBricks;
    }

    private static void updateTops(Brick brick, int[][] tops) {
        if (brick.a.x == brick.b.x && brick.a.y == brick.b.y) {
            tops[brick.a.y][brick.a.x] = brick.b.z;
        }
        if (brick.a.x == brick.b.x && brick.a.z == brick.b.z) {
            for (int y = brick.a.y; y <= brick.b.y; y++) {
                tops[y][brick.a.x] = brick.b.z;
            }
        }
        if (brick.a.y == brick.b.y && brick.a.z == brick.b.z) {
            for (int x = brick.a.x; x <= brick.b.x; x++) {
                tops[brick.a.y][x] = brick.b.z;
            }
        }
    }

    private static int findSettleHeight(Brick brick, int[][] tops) {
        int minSettleHeight = 0;

        for (int x = brick.a.x; x <= brick.b.x; x++) {
            minSettleHeight = Math.max(minSettleHeight, tops[brick.a.y][x]);
        }
        for (int y = brick.a.y; y <= brick.b.y; y++) {
            minSettleHeight = Math.max(minSettleHeight, tops[y][brick.a.x]);
        }

        return minSettleHeight + 1;
    }
}

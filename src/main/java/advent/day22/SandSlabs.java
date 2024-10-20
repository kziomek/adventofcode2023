package advent.day22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SandSlabs {
    public static void main(String[] args) throws IOException {
        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/example.txt"));
        //        List<Brick> bricks = Parser.parse(Path.of("src/main/resources/day22/my-input.txt"));

        System.out.println("Bricks");
        System.out.println(bricks);

        System.out.println("Settled bricks");
        List<Brick> settledBricks = settleBricks(bricks);
        System.out.println(settledBricks);
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

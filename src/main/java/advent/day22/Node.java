package advent.day22;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Brick brick;
    private int solelySupportsCounter = 0;
    List<Brick> supportedBricks = new ArrayList<>();
    List<Brick> supportingBricks = new ArrayList<>();

    public Node(Brick brick) {
        this.brick = brick;
    }

    public void setSupportedBricks(List<Brick> supportedBricks) {
        this.supportedBricks = supportedBricks;
    }
}

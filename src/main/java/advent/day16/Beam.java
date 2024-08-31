package advent.day16;

public class Beam {
    private char direction;
    private int i, j;

    public Beam(char direction, int i, int j) {
        this.direction = direction;
        this.i = i;
        this.j = j;
    }

    public char getDirection() {
        return direction;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

}

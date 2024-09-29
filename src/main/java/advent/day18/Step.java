package advent.day18;

public class Step {
    private final char direction;
    private final int length;
    private final String rgb;

    public Step(char direction, String length, String rgb) {
        this.direction = direction;
        this.length = Integer.parseInt(length);
        this.rgb = rgb;
    }

    public char getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public String getRgb() {
        return rgb;
    }
}

package advent.day18;

public class Step {
    private final char direction;
    private final int length;
    private final String rgb;

    public Step(char direction, String length, String rgb) {
//        this.direction = direction;
//        this.length = Integer.parseInt(length);
        this.rgb = rgb;

        switch(rgb.charAt(7)) {
            case '0' -> this.direction='R';
            case '1' -> this.direction='D';
            case '2' -> this.direction='L';
            case '3' -> this.direction='U';
            default -> throw new IllegalStateException();
        }
        this.length = Integer.parseInt(rgb.substring(2,7), 16);
//        System.out.println();

//        parse(rgb);


    }

//    private void parse(String rgb) {
//        switch(rgb.charAt(7)) {
//            case '0' -> this.direction='R';
//        }
//    }

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

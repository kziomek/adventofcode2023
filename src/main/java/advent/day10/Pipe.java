package advent.day10;

public class Pipe {
    int row, col;
    char value;

    Pipe next;
    Pipe previous;

    public Pipe(int r, int c, char value) {
        this.row = r;
        this.col = c;
        this.value = value;
    }

    public boolean locationEquals(Pipe other) {
        return this.row == other.row && this.col == other.col;
    }
}

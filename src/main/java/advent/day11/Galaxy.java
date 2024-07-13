package advent.day11;

public class Galaxy {
    int i, j;

    public Galaxy(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void print() {
        System.out.println("row " + i + " col " + j);
    }
}

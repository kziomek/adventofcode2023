package advent.day23;

import java.util.Set;

public class StackElement {
    int i, j;
    Set<String> seen;

    public StackElement(int i, int j, Set<String> seen) {
        this.i = i;
        this.j = j;
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "StackElement{" +
            "i=" + i +
            ", j=" + j +
            '}';
    }
}

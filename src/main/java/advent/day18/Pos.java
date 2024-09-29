package advent.day18;

import java.util.Objects;

public class Pos {
    int i, j;

    public Pos(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pos pos = (Pos) o;
        return i == pos.i && j == pos.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}

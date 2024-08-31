package advent.day16;

import java.util.HashSet;
import java.util.Set;

public class Pos {

    private final char value;

    Set<Character> seenDirection = new HashSet<>();

    public Pos(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public boolean seenBeamDirection(Beam beam) {
        return seenDirection.contains(beam.getDirection());
    }

    public void markSeenDirection(Beam beam) {
        seenDirection.add(beam.getDirection());
    }

    public boolean seenAny() {
        return !seenDirection.isEmpty();
    }
}

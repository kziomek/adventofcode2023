package advent.day8;

import java.util.ArrayList;
import java.util.List;

public class Input {

    private List<Node> starts = new ArrayList<>();

    private String instructions;

    public Input(Node start, String instructions) {
        this.starts.add(start);
        this.instructions = instructions;
    }

    public List<Node> getStarts() {
        return starts;
    }

    public String getInstructions() {
        return instructions;
    }
}

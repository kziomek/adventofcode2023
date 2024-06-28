package advent.day8;

public class Input {

    private Node start;

    private String instructions;

    public Input(Node start, String instructions) {
        this.start = start;
        this.instructions = instructions;
    }

    public Node getStart() {
        return start;
    }

    public String getInstructions() {
        return instructions;
    }
}

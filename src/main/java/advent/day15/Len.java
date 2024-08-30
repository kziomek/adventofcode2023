package advent.day15;

public class Len {
    private String label;
    private int size;

    public Len(String label, int size) {
        this.label = label;
        this.size = size;
    }

    public void replaceSize(int newSize) {
        size = newSize;
    }

    public String getLabel() {
        return label;
    }

    public int getSize() {
        return size;
    }
}

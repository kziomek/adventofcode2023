package advent.day20;

public class Pulse {
    String source;
    String target;
    boolean isHighPulse;

    public Pulse(String source, String target, boolean isHighPulse) {
        this.source = source;
        this.target = target;
        this.isHighPulse = isHighPulse;
    }

    @Override
    public String toString() {
        return source + " " + (isHighPulse ? "-high" : "-low") + "-> "+target;
    }
}

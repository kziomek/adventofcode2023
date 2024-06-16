package advent.day5;

public class AlmanacMap {
    long destinationRangeStart;
    long sourceRangeStart;
    long rangeLength;

    public AlmanacMap(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
        this.destinationRangeStart = destinationRangeStart;
        this.sourceRangeStart = sourceRangeStart;
        this.rangeLength = rangeLength;
    }

    public boolean match(long source) {
        return source >= sourceRangeStart && source < sourceRangeStart + rangeLength;
    }

    public long mapDestination(long source) {
        return destinationRangeStart + (source - sourceRangeStart);
    }

    public Range getDestinationRange() {
        return new Range(destinationRangeStart, destinationRangeStart + rangeLength - 1);
    }

    public Range getSourceRange() {
        return new Range(sourceRangeStart, sourceRangeStart + rangeLength - 1);
    }
}

package advent.day5.part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class IfYouGiveASeedAFertilizer {

    public static void main(String[] args) throws IOException {
                Almanac almanac = Parser.parse("src/main/resources/day5/example-part1.txt");
//        Almanac almanac = Parser.parse("src/main/resources/day5/my-input.txt"); //Result part2 59370572

        //                System.out.println("Lowest location " + almanac.process());
        //806029445 - part1

        System.out.println("Min Location: " + runPart2(almanac));
    }

    public static long runPart2(Almanac almanac) {
        List<Range> seedRanges = almanac.getSeedRanges();
        List<Range> soilRanges = map(split(seedRanges, almanac.getSeedToSoilSourceRanges()), almanac.seedsToSoilMaps);
        List<Range> fertilizerRanges = map(split(soilRanges, almanac.getSoilToFertilizerSourceRanges()), almanac.soilToFertilizerMaps);
        List<Range> waterRanges = map(split(fertilizerRanges, almanac.getFertilizerToWaterSourceRanges()), almanac.fertilizerToWaterMaps);
        List<Range> lightRanges = map(split(waterRanges, almanac.getWaterToLightSourceRanges()), almanac.waterToLightMaps);
        List<Range> temperatureRanges = map(split(lightRanges, almanac.getLightToTemperatureSourceRanges()), almanac.lightToTemperatureMaps);
        List<Range> humidityRanges = map(split(temperatureRanges, almanac.getTemperatureToHumiditySourceRanges()), almanac.temperatureToHumidityMaps);
        List<Range> locationRanges = map(split(humidityRanges, almanac.getHumidityToLocationSourceRanges()), almanac.humidityToLocationMaps);
        return locationRanges.stream().map(range -> range.start).mapToLong(l -> l).min().getAsLong();
    }

    private static List<Range> map(List<Range> sourceRanges, List<AlmanacMap> almanacMaps) {
        return sourceRanges.stream().map(range -> almanacMaps.stream()
                .filter(almanacMap -> almanacMap.match(range.start))
                .findFirst()
                .map(almanacMap -> new Range(range.start + almanacMap.getShift(), range.end + almanacMap.getShift()))
                .orElse(range))
            .toList();
    }

    private static List<Range> split(List<Range> toSplit, List<Range> rb) {
        Queue<Range> toSplitQueue = new LinkedList<>(toSplit);
        List<Range> split = new ArrayList<>();
        while (!toSplitQueue.isEmpty()) {
            Range range = toSplitQueue.poll();
            List<Range> result = split(range, rb);
            if (result.size() == 1) {
                split.addAll(result);
            } else {
                toSplitQueue.addAll(result);
            }
        }
        return split;
    }

    private static List<Range> split(Range toSplit, List<Range> ranges) {
        Optional<Range> overlappingOptional = ranges.stream()
            .filter(r -> !toSplit.disjoint(r))
            .findFirst();

        if (overlappingOptional.isEmpty()) {
            return List.of(toSplit);
        }

        return split(toSplit, overlappingOptional.get());
    }

    private static List<Range> split(Range a, Range b) {
        if (a.within(b)) {
            return List.of(a);
        }

        if (a.overlappingLeft(b)) {
            return List.of(new Range(a.start, b.start - 1), new Range(b.start, a.end));
        }

        if (a.overlappingRight(b)) {
            return List.of(new Range(a.start, b.end), new Range(b.end + 1, a.end));
        }

        return List.of(new Range(a.start, b.start - 1), new Range(b.start, b.end), new Range(b.end + 1, a.end));
    }
}

package advent.day5;

import java.io.IOException;
import java.util.List;

public class IfYouGiveASeedAFertilizer {

    // part 2 result 59370572

    public static void main(String[] args) throws IOException {
        Almanac almanac = Parser.parse("src/main/resources/day5/example-part1.txt");
        //                        Almanac almanac = Parser.parse("src/main/resources/day5/my-input.txt"); //Result 59370572

        //        System.out.println("Lowest location " + almanac.process());
        //806029445 - part1

        List<Range> locationRange = List.of(new Range(0, Long.MAX_VALUE));

        System.out.println("Flatten Location Destination Ranges");
        List<Range> flattenedHumidityToLocationDestRanges = RangeUtils.flatten(locationRange, almanac.getHumidityToLocationDestRanges());

        System.out.println("Map Location to Humidity Ranges");
        List<Range> humidityRanges = mapRanges(flattenedHumidityToLocationDestRanges, almanac.humidityToLocationMaps);

        System.out.println("Flattened Humidity Destination Ranges");
        List<Range> flattenedHumidityDestinationRanges = RangeUtils.flatten(humidityRanges, almanac.getTemperatureToHumidityRanges());

        System.out.println("Map Humidity to Temperature Ranges");
        List<Range> mappedHumidityRanges = mapRanges(flattenedHumidityDestinationRanges, almanac.temperatureToHumidityMaps);

        System.out.println("Flattened Temperature Destination Ranges");
        List<Range> flattenedTemperatureDestinationRanges = RangeUtils.flatten(mappedHumidityRanges, almanac.getLightToTemperatureDestRanges());

        System.out.println("Mapped Temperature to Light Ranges");
        List<Range> mappedTemperatureRanges = mapRanges(flattenedTemperatureDestinationRanges, almanac.lightToTemperatureMaps);

        System.out.println("Flattened Light Destination Ranges");
        List<Range> flattenedLightDestinationRanges = RangeUtils.flatten(mappedTemperatureRanges, almanac.getWaterToLightDestRanges());

        System.out.println("Map Light To Water Ranges");
        List<Range> mappedLightRanges = mapRanges(flattenedLightDestinationRanges, almanac.waterToLightMaps);

        System.out.println("Flatten Water Destination Ranges");
        List<Range> flattenedWaterDestinationRanges = RangeUtils.flatten(mappedLightRanges, almanac.getFertilizerToWaterDestRanges());

        System.out.println("Map Water to Fertilizer Ranges");
        List<Range> mappedWaterRanges = mapRanges(flattenedWaterDestinationRanges, almanac.fertilizerToWaterMaps);

        System.out.println("Flatten Fertilizer Destination Ranges");
        List<Range> flattenedFertilizerDestinationRanges = RangeUtils.flatten(mappedWaterRanges, almanac.getSoilToFertilizerDestRanges());

        System.out.println("Map Fertilizer to Soil Ranges");
        List<Range> mappedFertilizerRanges = mapRanges(flattenedFertilizerDestinationRanges, almanac.soilToFertilizerMaps);

        System.out.println("Flatten Soil Destination Ranges");
        List<Range> flattenedSoilDestinationRanges = RangeUtils.flatten(mappedFertilizerRanges, almanac.getSeedToSoilDestRanges());

        System.out.println("Map Soil to Seed Ranges");
        List<Range> mappedSoilRanges = mapRanges(flattenedSoilDestinationRanges, almanac.seedsToSoilMaps);

        System.out.println("Flatten Seed Ranges");
        List<Range> flattenedSeedRanges = RangeUtils.flatten(mappedSoilRanges, almanac.getSeedRanges());

        // filter flattenedSeedRanges with actual seed ranges
        List<Range> rangesWithinSeeds = filterByAlmanacSeedRange(flattenedSeedRanges, almanac);

        List<Long> seeds = rangesWithinSeeds.stream().map(range -> range.start).toList();
        long result = almanac.processSeeds(seeds);

        System.out.println("Result " + result);
    }

    private void runPart2() {

    }

    private static List<Range> filterByAlmanacSeedRange(List<Range> flattenedSeedRanges, Almanac almanac) {
        return flattenedSeedRanges
            .stream()
            .filter(flattenedSeedRange -> isWithinProvidedSeedRange(flattenedSeedRange, almanac))
            .toList();
    }

    private static boolean isWithinProvidedSeedRange(Range flattenedSeedRange, Almanac almanac) {
        for (Range seedRange : almanac.getSeedRanges()) {
            if (flattenedSeedRange.start >= seedRange.start && flattenedSeedRange.end <= seedRange.end) {
                return true;
            }
        }
        return false;
    }

    private static List<Range> mapRanges(List<Range> flattenedDestRanges, List<AlmanacMap> almanacMaps) {
        List<Range> ranges = flattenedDestRanges.stream()
            .map(range -> mapRange(range, almanacMaps))
            .toList();
        RangeUtils.print(ranges);
        return ranges;
    }

    private static Range mapRange(Range range, List<AlmanacMap> maps) {
        for (AlmanacMap almanacMap : maps) {
            if (almanacMap.matchByDestination(range)) {
                long shift = almanacMap.sourceRangeStart - almanacMap.destinationRangeStart;
                return new Range(range.start + shift, range.end + shift);
            }
        }

        return range;
    }
}

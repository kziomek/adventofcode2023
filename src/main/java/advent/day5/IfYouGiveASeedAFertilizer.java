package advent.day5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IfYouGiveASeedAFertilizer {

    //part 2 result 59370572

    public static void main(String[] args) throws IOException {
//        Almanac almanac = Parser.parse("src/main/resources/day5/example-part1.txt");
                Almanac almanac = Parser.parse("src/main/resources/day5/my-input.txt"); //Result 59370572

        //        System.out.println("Lowest location " + almanac.process());
        //806029445 - part1

        List<Range> totalRange = new ArrayList<>();
        totalRange.add(new Range(0, Long.MAX_VALUE));

        // get location ranges
        List<Range> locationDestinationRanges = getDestinationRanges(almanac.humidityToLocationMaps);
        List<Range> flattenedLocationDestinationRanges = RangeUtils.flatten(totalRange, locationDestinationRanges);
        System.out.println("Flattened Location Destination Ranges");
        RangeUtils.print(flattenedLocationDestinationRanges);

        // map location to humidity
        List<Range> mappedLocationRanges = mapRanges(flattenedLocationDestinationRanges, almanac.humidityToLocationMaps);
        System.out.println("Mapped Location Destination Ranges");
        RangeUtils.print(mappedLocationRanges);
        // map - cover gaps
        //        mappedLocationRanges = RangeUtils.flatten(totalRange, mappedLocationRanges);

        //get humidity destination ranges
        List<Range> humidityDestinationRanges = getDestinationRanges(almanac.temperatureToHumidityMaps);
        List<Range> flattenedHumidityDestinationRanges = RangeUtils.flatten(mappedLocationRanges, humidityDestinationRanges);
        System.out.println("Flattened Humidity Destination Ranges");
        RangeUtils.print(flattenedHumidityDestinationRanges);

        // map humidity to temperature
        List<Range> mappedHumidityRanges = mapRanges(flattenedHumidityDestinationRanges, almanac.temperatureToHumidityMaps);
        System.out.println("Mapped Humidity Ranges");
        RangeUtils.print(mappedHumidityRanges);
        // cover gaps
        //        mappedHumidityRanges = RangeUtils.flatten(totalRange, mappedHumidityRanges);

        // get temperature destination ranges
        List<Range> temperatureDestinationRanges = getDestinationRanges(almanac.lightToTemperatureMaps);
        List<Range> flattenedTemperatureDestinationRanges = RangeUtils.flatten(mappedHumidityRanges, temperatureDestinationRanges);
        System.out.println("Flattened Temperature Destination Ranges");
        RangeUtils.print(flattenedTemperatureDestinationRanges);

        // map temperature to light
        List<Range> mappedTemperatureRanges = mapRanges(flattenedTemperatureDestinationRanges, almanac.lightToTemperatureMaps);
        System.out.println("Mapped Temperature Ranges");
        RangeUtils.print(mappedTemperatureRanges);

        // get light destination ranges
        List<Range> lightDestinationRanges = getDestinationRanges(almanac.waterToLightMaps);
        List<Range> flattenedLightDestinationRanges = RangeUtils.flatten(mappedTemperatureRanges, lightDestinationRanges);
        System.out.println("Flattened Light Destination Ranges");
        RangeUtils.print(flattenedLightDestinationRanges);

        // map water to light
        List<Range> mappedLightRanges = mapRanges(flattenedLightDestinationRanges, almanac.waterToLightMaps);
        System.out.println("Mapped Light Ranges");
        RangeUtils.print(mappedLightRanges);

        // get water destination ranges
        List<Range> waterDestinationRanges = getDestinationRanges(almanac.fertilizerToWaterMaps);
        List<Range> flattenedWaterDestinationRanges = RangeUtils.flatten(mappedLightRanges, waterDestinationRanges);
        System.out.println("Flattened Water Destination Ranges");
        RangeUtils.print(flattenedWaterDestinationRanges);

        // map fertilizer to water
        List<Range> mappedWaterRanges = mapRanges(flattenedWaterDestinationRanges, almanac.fertilizerToWaterMaps);
        System.out.println("Mapped Water Ranges");
        RangeUtils.print(mappedWaterRanges);

        // get fertilizer destination ranges
        List<Range> fertilizerDestinationRanges = getDestinationRanges(almanac.soilToFertilizerMaps);
        List<Range> flattenedFertilizerDestinationRanges = RangeUtils.flatten(mappedWaterRanges, fertilizerDestinationRanges);
        System.out.println("Flattened Fertilizer Destination Ranges");
        RangeUtils.print(flattenedFertilizerDestinationRanges);

        // map soil to fertilizer
        List<Range> mappedFertilizerRanges = mapRanges(flattenedFertilizerDestinationRanges, almanac.soilToFertilizerMaps);
        System.out.println("Mapped Fertilizer Ranges");
        RangeUtils.print(mappedFertilizerRanges);

        // get soil destination ranges
        List<Range> soilDestRanges = getDestinationRanges(almanac.seedsToSoilMaps);
        List<Range> flattenedSoilDestinationRanges = RangeUtils.flatten(mappedFertilizerRanges, soilDestRanges);
        System.out.println("Flattened Soil Destination Ranges");
        RangeUtils.print(flattenedSoilDestinationRanges);

        // map seed to soil
        List<Range> mappedSoilRanges = mapRanges(flattenedFertilizerDestinationRanges, almanac.seedsToSoilMaps);
        System.out.println("Mapped Soil Ranges");
        RangeUtils.print(mappedSoilRanges);

        // get seed ranges
        List<Range> seedRanges = almanac.getSeedRanges();
        List<Range> flattenedSeedRanges = RangeUtils.flatten(mappedSoilRanges, seedRanges);
        System.out.println("Flattened Seed Ranges");
        RangeUtils.print(flattenedSeedRanges);


        // filter mappedSoilRanges by seeds
        List<Range> rangesWithinSeeds = flattenedSeedRanges.stream().filter(range -> {
            for (Range seedRange : seedRanges) {
                if (range.start >= seedRange.start && range.end <= seedRange.end) {
                    return true;
                }
            }
            return false;
        }).toList();

        List<Long> seeds = rangesWithinSeeds.stream().map(range -> range.start).toList();
        long result = almanac.processSeeds(seeds);
        System.out.println("Result " + result);
    }

    private static List<Range> mapRanges(List<Range> flattenedLocationDestinationRanges, List<AlmanacMap> almanacMaps) {
        return flattenedLocationDestinationRanges.stream()
            .map(range -> mapRange(range, almanacMaps))
            .toList();
    }

    private static List<Range> getDestinationRanges(List<AlmanacMap> almanacMaps) {
        return almanacMaps.stream().map(AlmanacMap::getDestinationRange).toList();
    }

    private static Range mapRange(Range range, List<AlmanacMap> maps) {
        for (AlmanacMap almanacMap : maps) {
            if (range.start >= almanacMap.destinationRangeStart && range.start < almanacMap.destinationRangeStart + almanacMap.rangeLength) {
                //                if (range.end != almanacMap.destinationRangeStart + almanacMap.rangeLength - 1) {
                //                    throw new IllegalStateException("Something wrong with range as it's not matching almanac");
                //                }
                long shift = almanacMap.sourceRangeStart - almanacMap.destinationRangeStart;
                return new Range(range.start + shift, range.end + shift);
            }
        }

        return range;
    }
}

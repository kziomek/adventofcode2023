package advent.day5.archive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Almanac {

    List<Long> seeds;
    List<AlmanacMap> seedsToSoilMaps;
    List<AlmanacMap> soilToFertilizerMaps;
    List<AlmanacMap> fertilizerToWaterMaps;
    List<AlmanacMap> waterToLightMaps;
    List<AlmanacMap> lightToTemperatureMaps;
    List<AlmanacMap> temperatureToHumidityMaps;
    List<AlmanacMap> humidityToLocationMaps;

    public Almanac(
        List<Long> seeds,
        List<AlmanacMap> seedsToSoilMaps,
        List<AlmanacMap> soilToFertilizerMaps,
        List<AlmanacMap> fertilizerToWaterMaps,
        List<AlmanacMap> waterToLightMaps,
        List<AlmanacMap> lightToTemperatureMaps,
        List<AlmanacMap> temperatureToHumidityMaps,
        List<AlmanacMap> humidityToLocationMaps
    ) {
        this.seeds = seeds;
        this.seedsToSoilMaps = seedsToSoilMaps;
        this.soilToFertilizerMaps = soilToFertilizerMaps;
        this.fertilizerToWaterMaps = fertilizerToWaterMaps;
        this.waterToLightMaps = waterToLightMaps;
        this.lightToTemperatureMaps = lightToTemperatureMaps;
        this.temperatureToHumidityMaps = temperatureToHumidityMaps;
        this.humidityToLocationMaps = humidityToLocationMaps;
    }

    public long process() {
        long currentlowestLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long location = seedToLocation(seed);
            currentlowestLocation = Math.min(currentlowestLocation, location);
        }
        return currentlowestLocation;
    }

    public long processSeeds(List<Long> seeds) {
        long currentlowestLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long location = seedToLocation(seed);
            currentlowestLocation = Math.min(currentlowestLocation, location);
        }
        return currentlowestLocation;
    }

    public long seedToLocation(Long seed) {
        long soil = matchAlmanacMap(seedsToSoilMaps, seed).map(a -> a.mapDestination(seed)).orElse(seed);
        long fertilizer = matchAlmanacMap(soilToFertilizerMaps, soil).map(a -> a.mapDestination(soil)).orElse(soil);
        long water = matchAlmanacMap(fertilizerToWaterMaps, fertilizer).map(a -> a.mapDestination(fertilizer)).orElse(fertilizer);
        long light = matchAlmanacMap(waterToLightMaps, water).map(a -> a.mapDestination(water)).orElse(water);
        long temperature = matchAlmanacMap(lightToTemperatureMaps, light).map(a -> a.mapDestination(light)).orElse(light);
        long humidity = matchAlmanacMap(temperatureToHumidityMaps, temperature).map(a -> a.mapDestination(temperature)).orElse(temperature);
        long location = matchAlmanacMap(humidityToLocationMaps, humidity).map(a -> a.mapDestination(humidity)).orElse(humidity);
        return location;
    }

    private Optional<AlmanacMap> matchAlmanacMap(List<AlmanacMap> maps, Long source) {
        return maps.stream().filter(am -> am.match(source)).findFirst();
    }

    public List<Range> getHumidityToLocationDestRanges() {
        return getDestinationRanges(this.humidityToLocationMaps);
    }

    public List<Range> getTemperatureToHumidityRanges() {
        return getDestinationRanges(this.temperatureToHumidityMaps);
    }

    public List<Range> getLightToTemperatureDestRanges() {
        return getDestinationRanges(this.lightToTemperatureMaps);
    }

    public List<Range> getWaterToLightDestRanges() {
        return getDestinationRanges(this.waterToLightMaps);
    }

    public List<Range> getFertilizerToWaterDestRanges() {
        return getDestinationRanges(this.fertilizerToWaterMaps);
    }

    public List<Range> getSoilToFertilizerDestRanges() {
        return getDestinationRanges(this.soilToFertilizerMaps);
    }

    public List<Range> getSeedToSoilDestRanges() {
        return getDestinationRanges(this.seedsToSoilMaps);
    }

    public List<Range> getSeedRanges() {
        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size() - 1; i = i + 2) {
            ranges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1));
        }
        return ranges;
    }

    private static List<Range> getDestinationRanges(List<AlmanacMap> almanacMaps) {
        return almanacMaps.stream().map(AlmanacMap::getDestinationRange).toList();
    }
}

package advent.day5.part2;

import java.util.ArrayList;
import java.util.List;

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

    public List<Range> getSeedRanges() {
        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size() - 1; i = i + 2) {
            ranges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1));
        }
        return ranges;
    }

    public List<Range> getSeedToSoilSourceRanges() {
        return getSourceRanges(this.seedsToSoilMaps);
    }

    public List<Range> getSoilToFertilizerSourceRanges() {
        return getSourceRanges(this.soilToFertilizerMaps);
    }

    public List<Range> getFertilizerToWaterSourceRanges() {
        return getSourceRanges(this.fertilizerToWaterMaps);
    }

    public List<Range> getWaterToLightSourceRanges() {
        return getSourceRanges(this.waterToLightMaps);
    }

    public List<Range> getLightToTemperatureSourceRanges() {
        return getSourceRanges(this.lightToTemperatureMaps);
    }

    public List<Range> getTemperatureToHumiditySourceRanges() {
        return getSourceRanges(this.temperatureToHumidityMaps);
    }

    public List<Range> getHumidityToLocationSourceRanges() {
        return getSourceRanges(this.humidityToLocationMaps);
    }

    private static List<Range> getSourceRanges(List<AlmanacMap> almanacMaps) {
        return almanacMaps.stream().map(AlmanacMap::getSourceRange).toList();
    }
}

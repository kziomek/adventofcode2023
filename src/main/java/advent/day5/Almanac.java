package advent.day5;

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
            long soil = matchAlmanacMap(seedsToSoilMaps, seed).map(a -> a.mapDestination(seed)).orElse(seed);
            long fertilizer = matchAlmanacMap(soilToFertilizerMaps, soil).map(a -> a.mapDestination(soil)).orElse(soil);
            long water = matchAlmanacMap(fertilizerToWaterMaps, fertilizer).map(a -> a.mapDestination(fertilizer)).orElse(fertilizer);
            long light = matchAlmanacMap(waterToLightMaps, water).map(a -> a.mapDestination(water)).orElse(water);
            long temperature = matchAlmanacMap(lightToTemperatureMaps, light).map(a -> a.mapDestination(light)).orElse(light);
            long humidity = matchAlmanacMap(temperatureToHumidityMaps, temperature).map(a -> a.mapDestination(temperature)).orElse(temperature);
            long location = matchAlmanacMap(humidityToLocationMaps, humidity).map(a -> a.mapDestination(humidity)).orElse(humidity);
            currentlowestLocation = Math.min(currentlowestLocation, location);
        }
        return currentlowestLocation;
    }

    private Optional<AlmanacMap> matchAlmanacMap(List<AlmanacMap> maps, Long source) {
        return maps.stream().filter(am -> am.match(source)).findFirst();
    }
}

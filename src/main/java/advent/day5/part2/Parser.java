package advent.day5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Parser {

    private static final String SEEDS = "seeds";
    private static final String SEED_TO_SOIL = "seed-to-soil";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static final String WATER_TO_LIGHT = "water-to-light";
    private static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location";

    public static Almanac parse(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));

        List<Long> seeds = new ArrayList<>();
        List<AlmanacMap> seedsToSoil = new ArrayList<>();
        List<AlmanacMap> soilToFertilizer = new ArrayList<>();
        List<AlmanacMap> fertilizerToWater = new ArrayList<>();
        List<AlmanacMap> waterToLight = new ArrayList<>();
        List<AlmanacMap> lightToTemperature = new ArrayList<>();
        List<AlmanacMap> temperatureToHumidity = new ArrayList<>();
        List<AlmanacMap> humidityToLocation = new ArrayList<>();

        String stage = SEEDS;

        for (String line : lines) {
            if (line.contains(SEEDS)) {
                seeds = parseSeeds(line);
            }

            if (line.isEmpty()) {
                continue;
            }

            if (line.contains(SEED_TO_SOIL)) {
                stage = SEED_TO_SOIL;
                continue;
            }

            if (line.contains(SOIL_TO_FERTILIZER)) {
                stage = SOIL_TO_FERTILIZER;
                continue;
            }

            if (line.contains(FERTILIZER_TO_WATER)) {
                stage = FERTILIZER_TO_WATER;
                continue;
            }

            if (line.contains(WATER_TO_LIGHT)) {
                stage = WATER_TO_LIGHT;
                continue;
            }

            if (line.contains(LIGHT_TO_TEMPERATURE)) {
                stage = LIGHT_TO_TEMPERATURE;
                continue;
            }

            if (line.contains(TEMPERATURE_TO_HUMIDITY)) {
                stage = TEMPERATURE_TO_HUMIDITY;
                continue;
            }

            if (line.contains(HUMIDITY_TO_LOCATION)) {
                stage = HUMIDITY_TO_LOCATION;
                continue;
            }

            if (SEED_TO_SOIL.equals(stage)) {
                seedsToSoil.add(parseAlmanacMap(line));
            }
            if (SOIL_TO_FERTILIZER.equals(stage)) {
                soilToFertilizer.add(parseAlmanacMap(line));
            }

            if (FERTILIZER_TO_WATER.equals(stage)) {
                fertilizerToWater.add(parseAlmanacMap(line));
            }

            if (WATER_TO_LIGHT.equals(stage)) {
                waterToLight.add(parseAlmanacMap(line));
            }

            if (LIGHT_TO_TEMPERATURE.equals(stage)) {
                lightToTemperature.add(parseAlmanacMap(line));
            }

            if (TEMPERATURE_TO_HUMIDITY.equals(stage)) {
                temperatureToHumidity.add(parseAlmanacMap(line));
            }
            if (HUMIDITY_TO_LOCATION.equals(stage)) {
                humidityToLocation.add(parseAlmanacMap(line));
            }
        }

        return new Almanac(seeds, seedsToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);
    }

    private static AlmanacMap parseAlmanacMap(String line) {
        List<Long> numbers = Stream.of(line.split(" ")).map(Long::parseLong).toList();
        return new AlmanacMap(numbers.get(0), numbers.get(1), numbers.get(2));
    }

    private static List<Long> parseSeeds(String line) {
        return Stream.of(line.split(":")[1].split(" "))
            .filter(s -> !s.isEmpty())
            .map(Long::parseLong)
            .toList();
    }
}

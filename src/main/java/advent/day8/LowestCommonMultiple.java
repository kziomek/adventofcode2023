package advent.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    https://www.geeksforgeeks.org/java-program-for-efficiently-print-all-prime-factors-of-a-given-number/
    https://www.youtube.com/watch?app=desktop&v=BSLem5-aDpI&ab_channel=TheOrganicChemistryTutor
 */
public class LowestCommonMultiple {

    public static long lcm(List<Long> numbers) {
        // collect prime factors for each number
        List<Map<Long, Long>> factorCounts = numbers
            .stream()
            .map(LowestCommonMultiple::getPrimaFactors)
            .toList();

        // calculate max count of each factor
        Map<Long, Long> factorMaxCount = new HashMap<>();
        for (Map<Long, Long> factorCountMap : factorCounts) {
            for (Long key : factorCountMap.keySet()) {
                factorMaxCount.merge(key, factorCountMap.get(key), Long::max);
            }
        }

        // calculate lowest common multiple
        return factorMaxCount
            .entrySet()
            .stream()
            .map(e -> e.getKey() * e.getValue())
            .reduce(1L, (a, b) -> a * b);
    }

    public static Map<Long, Long> getPrimaFactors(long number) {
        System.out.print("Prime factors of number " + number + " -> ");
        Map<Long, Long> primeFactorsCount = new HashMap<>();
        long factor = 2;
        while (number > 1) {
            if (number % factor == 0) {
                number = number / factor;
                primeFactorsCount.merge(factor, 1L, Long::sum);
                System.out.print(" " + factor);
            } else {
                factor++;
            }
        }
        System.out.println();
        return primeFactorsCount;
    }
}

package advent.day8;

public class LCM {

    //        https://www.geeksforgeeks.org/java-program-for-efficiently-print-all-prime-factors-of-a-given-number/
    //        https://www.youtube.com/watch?app=desktop&v=BSLem5-aDpI&ab_channel=TheOrganicChemistryTutor
    public static void printPrimaFactors(long number) {
        System.out.print("Prime factors of number " + number + " -> ");
        int factor = 2;
        while (number > 1) {
            if (number % factor == 0) {
                number = number / factor;
                System.out.print(" " + factor);
            } else {
                factor++;
            }
        }
        System.out.println();
    }
}

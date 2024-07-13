package advent.day11;

public class Expander {

    public static int[] rowExpander(char[][] arr, int expansionRation) {
        int[] rowExpand = new int[arr.length];
        int counter = 0;

        for (int i = 0; i < arr.length; i++) {
            boolean shouldExpand = true;
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == '#') {
                    shouldExpand = false;
                    break;
                }
            }
            if (shouldExpand) {
                counter += expansionRation - 1;
            }
            rowExpand[i] += counter;
        }

        return rowExpand;
    }

    public static int[] colExpander(char[][] arr, int expansionRation) {
        int[] colExpand = new int[arr[0].length];
        int counter = 0;
        for (int i = 0; i < arr[0].length; i++) {
            boolean shouldExpand = true;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j][i] == '#') {
                    shouldExpand = false;
                    break;
                }
            }
            if (shouldExpand) {
                counter += expansionRation - 1;
            }
            colExpand[i] += counter;
        }
        return colExpand;
    }

    // first version not workable for part 2
    public static char[][] expand(char[][] arr) {
        int rowExpansions = arr.length;
        int colExpansions = arr[0].length;
        boolean[] colHasGalaxy = new boolean[arr[0].length];
        boolean[] rowHasGalaxy = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == '#') {
                    rowExpansions--;
                    rowHasGalaxy[i] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < arr[0].length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j][i] == '#') {
                    colExpansions--;
                    colHasGalaxy[i] = true;
                    break;
                }
            }
        }
        int rowOffset = 0;
        char[][] expanded = new char[arr.length + rowExpansions][arr[0].length + colExpansions];
        for (int row = 0; row < arr.length; row++) {
            fillCol(arr, expanded, row, rowOffset, colHasGalaxy);
            if (!rowHasGalaxy[row]) {
                rowOffset++;
                fillCol(arr, expanded, row, rowOffset, colHasGalaxy);
            }
        }
        System.out.println("rowExpansions: " + (rowExpansions));
        System.out.println("colExpansions: " + (colExpansions));
        return expanded;
    }

    private static void fillCol(char[][] arr, char[][] expanded, int row, int rowOffset, boolean[] colHasGalaxy) {
        int colOffset = 0;
        for (int col = 0; col < arr[0].length; col++) {
            expanded[row + rowOffset][col + colOffset] = arr[row][col];
            if (!colHasGalaxy[col]) {
                colOffset++;
                expanded[row + rowOffset][col + colOffset] = arr[row][col];
            }
        }
    }
}

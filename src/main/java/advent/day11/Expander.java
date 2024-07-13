package advent.day11;

public class Expander {

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
        for (int i = 0; i < arr.length; i++) {
            if (!rowHasGalaxy[i]) {
                int colOffset = 0;
                for (int j = 0; j < arr[0].length; j++) {
                    if (!colHasGalaxy[j]) {
                        expanded[i + rowOffset][j + colOffset] = arr[i][j];
                        colOffset++;
                    }
                    expanded[i + rowOffset][j + colOffset] = arr[i][j];
                }
                rowOffset++;
            }
            int colOffset = 0;
            for (int j = 0; j < arr[0].length; j++) {
                if (!colHasGalaxy[j]) {
                    expanded[i + rowOffset][j + colOffset] = arr[i][j];
                    colOffset++;
                }
                expanded[i + rowOffset][j + colOffset] = arr[i][j];
            }
        }

        System.out.println("rowExpansions: " + (rowExpansions));
        System.out.println("colExpansions: " + (colExpansions));
        return expanded;
    }
}

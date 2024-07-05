package advent.day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PipeMaze {

    private static final int NORTH = 1;
    private static final int EAST = 2;
    private static final int SOUTH = 3;
    private static final int WEST = 4;

    //TODO BFS for part 1
    //TODO Ray Casting Algorithm for part 1


    public static void main(String[] args) throws IOException {
        //        char[][] field = Parser.parse("src/main/resources/day10/example1.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example2.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example3.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example4.txt");
        char[][] field = Parser.parse("src/main/resources/day10/my-input.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example-part2-1.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example-part2-2.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example-part2-3.txt");

        Pipe startPipe = findStart(field);
        buildBidirectionalPipe(startPipe, field);

        //Part 2
        // build fresh field with clean pipe
        // count turns to figure out which side is inside, which outside
        // follow pipe, for each inside mark all grounds recursivelly and count

        int length = measureLength(startPipe);
        System.out.println("Total length = " + length);
        System.out.println("Half is part 1 result = " + length / 2);

        // Part 2 - follow pipe and preserve orientation
        // when - or | mark left and right
        // then fill gaps with the same adjacent I/O
        // then figure out which touches border (O) -> other will be I
        // count I

        //        countTurns(startPipe);

        runPart2(field, startPipe);
    }

    private static void runPart2(char[][] field, Pipe startPipe) {
        char[][] freshField = new char[field.length][field[0].length];
        fillField(startPipe, freshField);
        printField(freshField);

        traverseAndMark(startPipe, freshField);
        System.out.println();
        printField(freshField);
        System.out.println();

        fillGaps(freshField);
        printField(freshField);

        char innerChar = figureOutInnerChar(freshField);
        System.out.println("Inner char " + innerChar);

        int result = countInner(innerChar, freshField);
        System.out.println("Inner count " + result);
    }

    private static int countInner(char innerChar, char[][] freshField) {
        int count = 0;
        for (char[] chars : freshField) {
            for (char aChar : chars) {
                if (aChar == innerChar) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void fillGaps(char[][] freshField) {
        for (int row = 0; row < freshField.length; row++) {
            for (int col = 0; col < freshField[0].length; col++) {
                if (freshField[row][col] == 'A' || freshField[row][col] == 'B') {
                    fillNeighbours(freshField, row + 1, col, freshField[row][col]);
                    fillNeighbours(freshField, row - 1, col, freshField[row][col]);
                    fillNeighbours(freshField, row, col + 1, freshField[row][col]);
                    fillNeighbours(freshField, row, col - 1, freshField[row][col]);
                }
            }
        }
    }

    private static void fillNeighbours(char[][] freshField, int row, int col, char value) {
        if (!isWithinField(row, col, freshField)) {
            return;
        }

        if (freshField[row][col] == '.') {
            freshField[row][col] = value;
            fillNeighbours(freshField, row + 1, col, value);
            fillNeighbours(freshField, row - 1, col, value);
            fillNeighbours(freshField, row, col + 1, value);
            fillNeighbours(freshField, row, col - 1, value);
        }
    }

    private static char figureOutInnerChar(char[][] freshField) {
        char outerChar = '.';
        for (char c : freshField[0]) {
            if (c == 'A' || c == 'B') {
                outerChar = c;
                break;
            }
        }
        if (outerChar == 'A') {
            return 'B';
        }
        if (outerChar == 'B') {
            return 'A';
        }
        throw new IllegalStateException();
    }

    private static void traverseAndMark(Pipe startPipe, char[][] freshField) {
        Pipe current = startPipe;

        do {
            int direction = direction(current, current.next);
            mark(freshField, current, direction);
            mark(freshField, current.next, direction);
            current = current.next;
        } while (current != startPipe);
    }

    private static void mark(char[][] freshField, Pipe current, int direction) {
        if (direction == SOUTH) {
            markA(freshField, current.row, current.col - 1);
            markB(freshField, current.row, current.col + 1);
        }
        if (direction == EAST) {
            markA(freshField, current.row + 1, current.col);
            markB(freshField, current.row - 1, current.col);
        }

        if (direction == NORTH) {
            markA(freshField, current.row, current.col + 1);
            markB(freshField, current.row, current.col - 1);
        }
        if (direction == WEST) {
            markA(freshField, current.row - 1, current.col);
            markB(freshField, current.row + 1, current.col);
        }
    }

    private static void markA(char[][] freshField, int row, int col) {
        if (!isWithinField(row, col, freshField)) {
            return;
        }
        if (freshField[row][col] == '.') {
            freshField[row][col] = 'A';
        }
    }

    private static void markB(char[][] freshField, int row, int col) {
        if (!isWithinField(row, col, freshField)) {
            return;
        }
        if (freshField[row][col] == '.') {
            freshField[row][col] = 'B';
        }
    }

    private static int direction(Pipe a, Pipe b) {
        if (b.row - a.row > 0) {
            return SOUTH;
        }
        if (b.row - a.row < 0) {
            return NORTH;
        }
        if (b.col - a.col > 0) {
            return EAST;
        }
        if (b.col - a.col < 0) {
            return WEST;
        }
        throw new IllegalStateException();
    }

    private static void printField(char[][] freshField) {
        for (char[] chars : freshField) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    private static void fillField(Pipe startPipe, char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = '.';
            }
        }

        Pipe current = startPipe;
        do {
            System.out.println("" + current.row + " " + current.col + " " + current.value);
            field[current.row][current.col] = current.value;
            current = current.next;
        } while (!current.locationEquals(startPipe));
    }

    private static void buildBidirectionalPipe(Pipe startPipe, char[][] field) {
        Pipe currentPipe = startPipe;

        do {
            currentPipe = connectNext(startPipe, currentPipe, field);
        } while (currentPipe != startPipe);
    }

    private static int measureLength(Pipe startPipe) {
        int length = 0;
        Pipe currentPipe = startPipe;
        do {
            currentPipe = currentPipe.next;
            length++;
        } while (!currentPipe.locationEquals(startPipe));
        return length;
    }

    private static Pipe connectNext(Pipe startPipe, Pipe currentPipe, char[][] field) {
        Pipe nextPipe = findNextPipe(currentPipe, field);
        if (nextPipe.locationEquals(startPipe)) {
            nextPipe = startPipe;
        }

        currentPipe.next = nextPipe;
        nextPipe.previous = currentPipe;
        return nextPipe;
    }

    private static Pipe findNextPipe(Pipe currentPipe, char[][] field) {
        System.out.println("Current pipe (r,c) -> " + currentPipe.row + " " + currentPipe.col);
        List<Pipe> neighbourPipes = findConnectingPipes(currentPipe, field);
        if (currentPipe.previous == null) {
            return neighbourPipes.getFirst();
        }
        Pipe nextPipe = neighbourPipes
            .stream()
            .filter(pipe -> !pipe.locationEquals(currentPipe.previous))
            .findFirst()
            .get();
        return nextPipe;
    }

    private static List<Pipe> findConnectingPipes(Pipe currentPipe, char[][] field) {
        List<Pipe> connectingPipes = new ArrayList<>();
        if (currentPipe.value == '|') {
            getNorthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getSouthConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }
        if (currentPipe.value == '-') {
            getWestConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getEastConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }
        if (currentPipe.value == 'L') {
            getNorthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getEastConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }
        if (currentPipe.value == 'J') {
            getNorthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getWestConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }
        if (currentPipe.value == '7') {
            getSouthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getWestConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }
        if (currentPipe.value == 'F') {
            getSouthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getEastConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }

        if (currentPipe.value == 'S') {
            getNorthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getSouthConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getWestConnection(currentPipe, field).ifPresent(connectingPipes::add);
            getEastConnection(currentPipe, field).ifPresent(connectingPipes::add);
        }

        return connectingPipes;
    }

    private static Optional<Pipe> getNorthConnection(Pipe currentPipe, char[][] field) {
        int r = currentPipe.row - 1;
        int c = currentPipe.col;
        if (isWithinField(r, c, field)) {
            char value = field[r][c];
            if (value == '|' || value == '7' || value == 'F' || value == 'S') {
                return Optional.of(new Pipe(r, c, field[r][c]));
            }
        }
        return Optional.empty();
    }

    private static Optional<Pipe> getSouthConnection(Pipe currentPipe, char[][] field) {
        int r = currentPipe.row + 1;
        int c = currentPipe.col;
        if (isWithinField(r, c, field)) {
            char value = field[r][c];
            if (value == '|' || value == 'J' || value == 'L' || value == 'S') {
                return Optional.of(new Pipe(r, c, value));
            }
        }
        return Optional.empty();
    }

    private static Optional<Pipe> getWestConnection(Pipe currentPipe, char[][] field) {
        int r = currentPipe.row;
        int c = currentPipe.col - 1;
        if (isWithinField(r, c, field)) {
            char value = field[r][c];
            if (value == '-' || value == 'L' || value == 'F' || value == 'S') {
                return Optional.of(new Pipe(r, c, value));
            }
        }
        return Optional.empty();
    }

    private static Optional<Pipe> getEastConnection(Pipe currentPipe, char[][] field) {
        int r = currentPipe.row;
        int c = currentPipe.col + 1;
        if (isWithinField(r, c, field)) {
            char value = field[r][c];
            if (value == '-' || value == '7' || value == 'J' || value == 'S') {
                return Optional.of(new Pipe(r, c, value));
            }
        }
        return Optional.empty();
    }

    private static boolean isWithinField(int r, int c, char[][] field) {
        return r >= 0 && c >= 0 & r < field.length && c < field[0].length;
    }

    private static Pipe findStart(char[][] field) {
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[0].length; c++) {
                if (field[r][c] == 'S') {
                    return new Pipe(r, c, field[r][c]);
                }
            }
        }
        return null;
    }
}

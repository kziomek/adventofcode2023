package advent.day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PipeMaze {

    public static void main(String[] args) throws IOException {
        //        char[][] field = Parser.parse("src/main/resources/day10/example1.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example2.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example3.txt");
        //        char[][] field = Parser.parse("src/main/resources/day10/example4.txt");
        char[][] field = Parser.parse("src/main/resources/day10/my-input.txt");

        Pipe startPipe = findStart(field);
        buildBidirectionalPipe(startPipe, field);

        int length = measureLength(startPipe);
        System.out.println("Total length = " + length);
        System.out.println("Half = " + length / 2);
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

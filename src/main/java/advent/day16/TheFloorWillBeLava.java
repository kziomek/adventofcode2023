package advent.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;

public class TheFloorWillBeLava {

    public static void main(String[] args) throws IOException {
        //        String input = "src/main/resources/day16/example.txt";
        String input = "src/main/resources/day16/my-input.txt";

        Pos[][] contraption = loadContraption(input);

        int maxCount = 0;
        for (int i = 0; i < contraption.length; i++) {
            reset(contraption);
            Beam initBeam = new Beam('>', i, 0);
            int count = countEnergisedTiles(initBeam, contraption);
            maxCount = Math.max(count, maxCount);
        }

        for (int j = 0; j < contraption[0].length; j++) {
            reset(contraption);
            Beam initBeam = new Beam('v', 0, j);
            int count = countEnergisedTiles(initBeam, contraption);
            maxCount = Math.max(count, maxCount);
        }

        for (int i = 0; i < contraption.length; i++) {
            reset(contraption);
            Beam initBeam = new Beam('<', i, contraption[0].length);
            int count = countEnergisedTiles(initBeam, contraption);
            maxCount = Math.max(count, maxCount);
        }

        for (int j = 0; j < contraption[0].length; j++) {
            reset(contraption);
            Beam initBeam = new Beam('^', contraption.length, j);
            int count = countEnergisedTiles(initBeam, contraption);
            maxCount = Math.max(count, maxCount);
        }

        System.out.println("Max energised: " + maxCount);
        //        countEnergisedTiles(initBeam, contraption);
    }

    private static void reset(Pos[][] contraption) {
        for (Pos[] posRows : contraption) {
            for (Pos pos : posRows) {
                pos.reset();
            }
        }
    }

    private static Pos[][] loadContraption(String input) throws IOException {
        return Files.readAllLines(Path.of(input))
            .stream().map(line -> line.chars().mapToObj(c -> new Pos((char) c)).toArray(Pos[]::new))
            .toArray(Pos[][]::new);
    }

    private static int countEnergisedTiles(Beam initBeam, Pos[][] contraption) {
        Queue<Beam> beamQueue = new LinkedList<>();
        beamQueue.add(initBeam);

        traverse(contraption, beamQueue);
        int count = countSeen(contraption);
        System.out.println(count);
        return count;
    }

    private static int countSeen(Pos[][] contraption) {
        int count = 0;
        for (Pos[] row : contraption) {
            for (Pos pos : row) {
                if (pos.seenAny()) {
                    count++;
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        return count;
    }

    private static void traverse(Pos[][] contraption, Queue<Beam> beamQueue) {
        while (!beamQueue.isEmpty()) {
            Beam beam = beamQueue.poll();

            if (!withinContraption(beam, contraption.length, contraption[0].length)) {
                continue;
            }

            Pos pos = contraption[beam.getI()][beam.getJ()];
            if (pos.seenBeamDirection(beam)) {
                continue;
            }
            pos.markSeenDirection(beam);

            //figure out next beam(s)
            // ><^v multiply by  .-|/\
            ;
            if (beam.getDirection() == '>' && (pos.getValue() == '.' || pos.getValue() == '-')) {
                beamQueue.add(new Beam('>', beam.getI(), beam.getJ() + 1));
            }
            if (beam.getDirection() == '>' && pos.getValue() == '/') {
                beamQueue.add(new Beam('^', beam.getI() - 1, beam.getJ()));
            }
            if (beam.getDirection() == '>' && pos.getValue() == '\\') {
                beamQueue.add(new Beam('v', beam.getI() + 1, beam.getJ()));
            }
            if (beam.getDirection() == '>' && pos.getValue() == '|') {
                beamQueue.add(new Beam('^', beam.getI() - 1, beam.getJ()));
                beamQueue.add(new Beam('v', beam.getI() + 1, beam.getJ()));
            }

            if (beam.getDirection() == '<' && (pos.getValue() == '.' || pos.getValue() == '-')) {
                beamQueue.add(new Beam('<', beam.getI(), beam.getJ() - 1));
            }
            if (beam.getDirection() == '<' && pos.getValue() == '/') {
                beamQueue.add(new Beam('v', beam.getI() + 1, beam.getJ()));
            }
            if (beam.getDirection() == '<' && pos.getValue() == '\\') {
                beamQueue.add(new Beam('^', beam.getI() - 1, beam.getJ()));
            }
            if (beam.getDirection() == '<' && pos.getValue() == '|') {
                beamQueue.add(new Beam('^', beam.getI() - 1, beam.getJ()));
                beamQueue.add(new Beam('v', beam.getI() + 1, beam.getJ()));
            }

            if (beam.getDirection() == '^' && (pos.getValue() == '.' || pos.getValue() == '|')) {
                beamQueue.add(new Beam('^', beam.getI() - 1, beam.getJ()));
            }
            if (beam.getDirection() == '^' && pos.getValue() == '-') {
                beamQueue.add(new Beam('<', beam.getI(), beam.getJ() - 1));
                beamQueue.add(new Beam('>', beam.getI(), beam.getJ() + 1));
            }
            if (beam.getDirection() == '^' && pos.getValue() == '/') {
                beamQueue.add(new Beam('>', beam.getI(), beam.getJ() + 1));
            }
            if (beam.getDirection() == '^' && pos.getValue() == '\\') {
                beamQueue.add(new Beam('<', beam.getI(), beam.getJ() - 1));
            }

            if (beam.getDirection() == 'v' && (pos.getValue() == '.' || pos.getValue() == '|')) {
                beamQueue.add(new Beam('v', beam.getI() + 1, beam.getJ()));
            }
            if (beam.getDirection() == 'v' && pos.getValue() == '-') {
                beamQueue.add(new Beam('<', beam.getI(), beam.getJ() - 1));
                beamQueue.add(new Beam('>', beam.getI(), beam.getJ() + 1));
            }
            if (beam.getDirection() == 'v' && pos.getValue() == '/') {
                beamQueue.add(new Beam('<', beam.getI(), beam.getJ() - 1));
            }
            if (beam.getDirection() == 'v' && pos.getValue() == '\\') {
                beamQueue.add(new Beam('>', beam.getI(), beam.getJ() + 1));
            }
        }
    }

    private static boolean withinContraption(Beam beam, int lengthI, int lengthJ) {
        return beam != null && beam.getI() >= 0 && beam.getI() < lengthI && beam.getJ() >= 0 && beam.getJ() < lengthJ;
    }
}

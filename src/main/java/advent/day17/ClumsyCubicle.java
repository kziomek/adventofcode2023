package advent.day17;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class ClumsyCubicle {

    public static void main(String[] args) throws IOException {

        int res = 2 + 4 + 1 + 1 + 5 + 4 + 5 + 3 + 2 + 3 + 1 + 3 + 5 + 4 + 2 + 4 + 5 + 3 + 5 + 6 + 5 + 3 + 7 + 3 + 3 + 6 + 3 + 3 + 3;
        System.out.println(res);

//        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/example.txt");
//        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/unfortunate-route.txt");
        //        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/example-short.txt");
                CityBlock[][] blocks = Parser.parse("src/main/resources/day17/my-input.txt");
        buildGraph(blocks);

        PriorityQueue<DirectionBlock> queue = new PriorityQueue<>(Comparator.comparingInt(DirectionBlock::getTotalCost));

        DirectionBlock firstDirectionBlock = new DirectionBlock(blocks[0][0], '.', 0);
        firstDirectionBlock.setTotalCost(0);
        queue.add(firstDirectionBlock); // TODO we should go right and down so we need to add ?

        while (!queue.isEmpty()) {
            DirectionBlock currentDirectionBlock = queue.poll();
            System.out.println("currentDirectionBlock " + currentDirectionBlock.getOriginal().getI() + " " + currentDirectionBlock.getOriginal()
                .getJ() + " " + currentDirectionBlock.getEntryDirection() + " total: " + currentDirectionBlock.getTotalCost());

            List<DirectionBlock> possibleTargets = getPossibleTargets(currentDirectionBlock);

            for (DirectionBlock possibleTarget : possibleTargets) {
                Optional<DirectionBlock> existingDirectionBlock =
                    possibleTarget.getOriginal().directionBlockSet.stream().filter(db -> db.getEntryDirection() == possibleTarget.getEntryDirection() && db.getStraightCounter() == possibleTarget.getStraightCounter()).findFirst();
                if (existingDirectionBlock.isEmpty() || existingDirectionBlock.get().getTotalCost() >= currentDirectionBlock.getTotalCost() + possibleTarget.getOriginal().getValue()) { // TODO IN THE SAME DIRECTION
                    queue.remove(possibleTarget);
                    possibleTarget.setTotalCost(currentDirectionBlock.getTotalCost() + possibleTarget.getOriginal().getValue());
                    queue.add(possibleTarget);
                    possibleTarget.getOriginal().directionBlockSet.add(possibleTarget);
                    possibleTarget.setPrev(currentDirectionBlock);
                    System.out.println("Updated [" + possibleTarget.getOriginal().getI() + "][" + possibleTarget.getOriginal()
                        .getJ() + "] " + possibleTarget.getEntryDirection() + " " + possibleTarget.getTotalCost() + " " + possibleTarget.getStraightCounter());
                    System.out.print("");
                } else {
                    System.out.println("Skipped [" + possibleTarget.getOriginal().getI() + "][" + possibleTarget.getOriginal()
                        .getJ() + "] " + possibleTarget.getEntryDirection() + " " + (currentDirectionBlock.getTotalCost() + possibleTarget.getOriginal().getValue()) + " " + possibleTarget.getStraightCounter());
                }
            }
            //            print(queue);
            //            printTotal(blocks);
            System.out.println();
        }

        //        print(blocks);
        //        printTrack(blocks);
        printAllTracks(blocks);
        printBestResult(blocks);

        System.out.println();
    }

    private static void printBestResult(CityBlock[][] blocks) {
        DirectionBlock result = blocks[blocks.length - 1][blocks[0].length - 1].directionBlockSet.stream().filter(db -> db.getStraightCounter() >= 4).min(Comparator.comparingInt(DirectionBlock::getTotalCost)).get();
        System.out.println("Best route " + result.getTotalCost());
    }

    private static List<DirectionBlock> getPossibleTargets(DirectionBlock block) {
        final char direction = block.getEntryDirection();
        int straightCount = block.getStraightCounter();

        System.out.println("direction " + direction + " straightCount " + straightCount);

        char reversedDirection = reverseDirection(direction);
        return block.getOriginal().getEdges().entrySet() //
            .stream()
            .filter(e -> e.getKey() != reversedDirection)
            .filter(e ->  block.getEntryDirection() == '.'
                || (e.getKey() != block.getEntryDirection() && straightCount >= 4)
                || (e.getKey() == block.getEntryDirection() && straightCount < 10))
            .map(e -> new DirectionBlock(e.getValue(), e.getKey(), e.getKey() == block.getEntryDirection() ? straightCount + 1 : 1))
            .collect(Collectors.toList()); // Here we get access to target blocks
    }

    private static char reverseDirection(char direction) {
        switch (direction) {
            case '>':
                return '<';
            case '<':
                return '>';
            case '^':
                return 'v';
            case 'v':
                return '^';
        }
        return '.';
    }

    private static CityBlock buildGraph(CityBlock[][] blocks) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                CityBlock block = blocks[i][j];
                if (i > 0) {
                    block.addEdge('^', blocks[i - 1][j]);
                }
                if (i < blocks.length - 1) {
                    block.addEdge('v', blocks[i + 1][j]);
                }
                if (j > 0) {
                    block.addEdge('<', blocks[i][j - 1]);
                }
                if (j < blocks[0].length - 1) {
                    block.addEdge('>', blocks[i][j + 1]);
                }
            }
        }
        return blocks[0][0];
    }

    private static void printAllTracks(CityBlock[][] blocks) {
        CityBlock targetBlock = blocks[blocks.length - 1][blocks[0].length - 1];
        for (DirectionBlock directionBlock : targetBlock.directionBlockSet) {
            System.out.println("Result " + directionBlock.getEntryDirection() + " " + directionBlock.getTotalCost());
            System.out.println();
            printTrack(blocks, directionBlock);
            System.out.println();
        }
    }

    private static void printTrack(CityBlock[][] blocks, DirectionBlock directionBlock) {
        char[][] track = new char[blocks.length][blocks[0].length];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                track[i][j] = (char) (blocks[i][j].getValue() + '0');
            }
        }

        DirectionBlock current = directionBlock;

        while (current != null) {
            track[current.getOriginal().getI()][current.getOriginal().getJ()] = current.getEntryDirection();
            current = current.getPrev();
        }

        for (char[] chars : track) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
}

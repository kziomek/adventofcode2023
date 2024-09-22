package advent.day17;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class ClumsyCubicle {

    public static void main(String[] args) throws IOException {

        int res = 2 + 4 + 1 + 1 + 5 + 4 + 5 + 3 + 2 + 3 + 1 + 3 + 5 + 4 + 2 + 4 + 5 + 3 + 5 + 6 + 5 + 3 + 7 + 3 + 3 + 6 + 3 + 3 + 3;
        System.out.println(res);

//        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/example.txt");
        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/example-short.txt");
//        CityBlock[][] blocks = Parser.parse("src/main/resources/day17/my-input.txt");
        buildGraph(blocks);


//        blocks[0][0].setTotalCost(blocks[0][0].getValue());
        blocks[0][0].setTotalCost(0);
//
//        blocks[blocks.length-1][blocks[0].length-1].setTotalCost(blocks[blocks.length-1][blocks[0].length-1].getValue());

        // TODO we need to add target with direction when equal
        PriorityQueue<CityBlock> queue = new PriorityQueue<>(Comparator.comparingInt(CityBlock::getTotalCost));
        queue.add(blocks[0][0]); // TODO we should go right and down so we need to add ?
//        for (CityBlock[] blocksRow : blocks) {
//            queue.addAll(Arrays.asList(blocksRow));
//        }

        while (!queue.isEmpty()) {
            CityBlock block = queue.poll();
            System.out.println("block " + block.getI() + " " + block.getJ() + " " + block.getFromDirection() + " total: " + block.getTotalCost() );
            if (block.isSeen()) {
                System.out.println("seen");
                continue;
            }
            block.setSeen(); // TODO node can be seen from many directions

            List<Map.Entry<Character, CityBlock>> possibleTargets = getPossibleTargets(block);
            for (Map.Entry<Character, CityBlock> possibleTarget : possibleTargets) {
                CityBlock target = possibleTarget.getValue();
                Character targetDirection = possibleTarget.getKey();

                // TODO MODIFY ALGORITHM TO COUNT HEAT
                if (target.getTotalCost() >= block.getTotalCost() + target.getValue()) {
//                    int qsize = queue.size();
                    queue.remove(target);
//                    if (qsize -1 != queue.size()) {
//                        System.out.println("check me");
//                    }
                    target.updateTotalCost(block.getTotalCost() + target.getValue());
                    target.setPrev(block);
                    target.setFromDirection(targetDirection);
                    queue.add(target);
                    System.out.println("Updated " +target.getI() +" "+ target.getJ() + " " + target.getFromDirection());
                }

//                if (target.getTotalCost() > block.getTotalCost() + target.getValue()) {
//                    queue.remove(target);
//                    target.updateTotalCost(block.getTotalCost());
//                    target.setPrev(block);
//                    target.setFromDirection(targetDirection);
//                    queue.add(target);
//                }
            }
            printTotal(blocks);
            System.out.println();
        }



        print(blocks);

        System.out.println();
    }



    private static List<Map.Entry<Character, CityBlock>> getPossibleTargets(CityBlock block) {
        final char direction = block.getFromDirection();

        int count = 1;
        CityBlock c = block;
        while (c.getPrev() != null && (c.getPrev().getFromDirection() == direction)) {
            count++;
            c = c.getPrev();
        }
        final int straightCount = count;
        System.out.println("direction " + direction + " straightCount " + straightCount);

        char reversedDirection = reverseDirection(direction);
        return block.getEdges().entrySet()
            .stream()
//            .filter(e -> !e.getValue().isSeen())
            .filter(e -> e.getKey() != reversedDirection)
            .filter (e -> e.getKey() != block.getFromDirection() || e.getKey() == block.getFromDirection() && straightCount < 3)
            .collect(Collectors.toList());
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

    private static void printTotal(CityBlock[][] blocks) {
        System.out.println("Total cost");
        for (CityBlock[] blockRow : blocks) {
            for (CityBlock cityBlock : blockRow) {
                System.out.print(String.format("%4d", cityBlock.getTotalCost()) );
            }
            System.out.println();
        }
    }

    private static void print(CityBlock[][] blocks) {
        for (CityBlock[] blockRow : blocks) {
            for (CityBlock cityBlock : blockRow) {
                System.out.print(cityBlock.getFromDirection());
            }
            System.out.println();
        }

        System.out.println();
        printTotal(blocks);

        System.out.println();
        String[][] arr = new String[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                arr[i][j] = "" + blocks[i][j].getValue();
            }
        }

        CityBlock cb = blocks[blocks.length - 1][blocks[0].length - 1];
        while (cb.getPrev() != null) {
            arr[cb.getI()][cb.getJ()] = "" + cb.getFromDirection();
            cb = cb.getPrev();
        }

        for (String[] strings : arr) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
}

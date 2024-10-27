package advent.day23;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO stack overflow -> refactor solution to use queue or optimize sets
public class LongWalk {

    // todo build graph with nodes in intersections and edges lengths, then try to find longest path with memoization (intersection, remaining nodes)
    public static void main(String[] args) throws IOException {
//        char[][] grid = Parser.parse(Path.of("src/main/resources/day23/example.txt"));
                                        char[][] grid = Parser.parse(Path.of("src/main/resources/day23/my-input.txt"));

        printGrid(grid);

        Node startNode = new Node(0, 1);
        validateIsPath(grid, startNode);
        Node endNode = new Node(grid.length - 1, grid[0].length - 2);
        validateIsPath(grid, endNode);

        Set<Node> nodes = buildNodes(grid);
        nodes.add(startNode);
        nodes.add(endNode);
        System.out.println(nodes);

        updateAdjacents(grid, nodes);

        System.out.println(nodes);

        // draw graph on paper

        // TODO start from start and traverse with BSF

        //        int intersections = countIntersections(grid);
        //        System.out.println("Intersections " + intersections);
        //        System.exit(0);
        //
        //        int maxWalkLength = findLongestPath(grid);
        //
        //        System.out.println("Max walk is " + maxWalkLength);
    }

    private static void updateAdjacents(char[][] grid, Set<Node> nodes) {
        for (Node node : nodes) {

            node.setEdges(findAdjacents(grid, node));
        }
    }

    private static List<Edge> findAdjacents(char[][] grid, Node node) {
        List<Edge> edges = new ArrayList<>();
        Set<String> seen = Set.of(key(node.i, node.j));

        if (canGo(grid, node.i - 1, node.j, seen, new HashSet<>())) {
            edges.add(goToIntersectionNode(grid, node.i - 1, node.j, seen));
        }
        if (canGo(grid, node.i + 1, node.j, seen, new HashSet<>())) {
            edges.add(goToIntersectionNode(grid, node.i + 1, node.j, seen));
        }
        if (canGo(grid, node.i, node.j - 1, seen, new HashSet<>())) {
            edges.add(goToIntersectionNode(grid, node.i, node.j - 1, seen));
        }
        if (canGo(grid, node.i, node.j + 1, seen, new HashSet<>())) {
            edges.add(goToIntersectionNode(grid, node.i, node.j + 1, seen));
        }

        return edges;
    }

    private static Edge goToIntersectionNode(char[][] grid, int i, int j, Set<String> rootSeen) {
        int count = 1;
        Set<String> seen = new HashSet<>(rootSeen);
        seen.add(key(i, j));
        Node currentStep = new Node(i, j);

        while (countPossibleDirections(grid, currentStep.i, currentStep.j, seen) == 1) {
            currentStep = findNextStep(grid, currentStep, seen);
            seen.add(key(currentStep.i, currentStep.j));
            count++;
        }
        return new Edge(count, currentStep);
    }

    private static Node findNextStep(char[][] grid, Node node, Set<String> seen) {
        if (canGo(grid, node.i - 1, node.j, seen, new HashSet<>())) {
            return new Node(node.i - 1, node.j);
        }
        if (canGo(grid, node.i + 1, node.j, seen, new HashSet<>())) {
            return new Node(node.i + 1, node.j);
        }
        if (canGo(grid, node.i, node.j - 1, seen, new HashSet<>())) {
            return new Node(node.i, node.j - 1);
        }
        if (canGo(grid, node.i, node.j + 1, seen, new HashSet<>())) {
            return new Node(node.i, node.j + 1);
        }
        throw new IllegalStateException("should not reach here");
    }

    private static void validateIsPath(char[][] grid, Node startNode) {
        if (grid[startNode.i][startNode.j] != '.') {
            throw new IllegalStateException("it's not a path");
        }
    }

    private static Set<Node> buildNodes(char[][] grid) {
        Set<Node> nodes = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '#') {
                    continue;
                }
                int possibleDirections = countPossibleDirections(grid, i, j, new HashSet<>());
                if (possibleDirections > 2) {
                    System.out.println(key(i, j));
                    nodes.add(new Node(i, j));
                }
            }
        }
        return nodes;
    }

    private static int countIntersections(char[][] grid) {
        int intersections = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '#') {
                    continue;
                }
                int possibleDirections = countPossibleDirections(grid, i, j, new HashSet<>());
                if (possibleDirections > 2) {
                    System.out.println(key(i, j));
                    intersections++;
                }
            }
        }
        return intersections;
    }

    private static String key(int i, int j) {
        return "" + i + "_" + j;
    }

    private static int findLongestPath(char[][] grid) {
        //        Set<String> seen = new HashSet<>();
        //        seen.add(key(0, 1));
        Set<String> deadEnd = new HashSet<>();
        List<Integer> walkLengths = new ArrayList<>();

        Deque<StackElement> stack = new LinkedList<>();
        stack.add(new StackElement(0, 1, new HashSet<>())); // start position

        while (!stack.isEmpty()) {
            System.out.println("Stack size " + stack.size());
            StackElement walk = stack.pollLast();
            //            System.out.println(walk);
            if (walk.seen.contains(key(walk.i, walk.j))) {
                System.out.println("Been here before");
                //been here before
                continue;
            }
            walk.seen.add(key(walk.i, walk.j));
            if (walk.i == grid.length - 1) {
                walkLengths.add((walk.seen.size() - 1));
                System.out.println("Walk length " + (walk.seen.size() - 1)); // deduct 1 as start position
                // the end
                //            printGrid(grid, seen);
                continue;
            }
            int possibleDirections = countPossibleDirections(grid, walk.i, walk.j, walk.seen);
            int deadEndCount = countDeadEnd(grid, walk.i, walk.j, deadEnd);

            // here try walking in any direction
            if ((possibleDirections > 0 && possibleDirections == deadEndCount)) { //dead end
                System.out.println("dead end " + walk + " grid value " + grid[walk.i][walk.j] + " seen size " + walk.seen.size());
                printGrid(grid, walk.seen);
                deadEnd.add(key(walk.i, walk.j));
                continue;
            }

            if (possibleDirections != 1) { // todo we have to clone seen set
                System.out.println("possible directions " + possibleDirections + " at [" + walk.i + "][" + walk.j + "]");
            }

            //            if (canGo(grid, walk.i - 1, walk.j, walk.seen, deadEnd) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '^')) {
            if (canGo(grid, walk.i - 1, walk.j, walk.seen, deadEnd)) {
                //                Set<String> seen = walk.seen;
                //                if (possibleDirections > 1) {
                //                    seen = new HashSet<>(walk.seen);
                //                }
                stack.add(new StackElement(walk.i - 1, walk.j, new HashSet<>(walk.seen)));
            }
            //            if (canGo(grid, walk.i + 1, walk.j, walk.seen, deadEnd) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == 'v')) {
            if (canGo(grid, walk.i + 1, walk.j, walk.seen, deadEnd)) {
                //                Set<String> seen = walk.seen;
                //                if (possibleDirections > 1) {
                //                    seen = new HashSet<>(walk.seen);
                //                }
                stack.add(new StackElement(walk.i + 1, walk.j, new HashSet<>(walk.seen)));
            }
            //            if (canGo(grid, walk.i, walk.j - 1, walk.seen, deadEnd) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '<')) {
            if (canGo(grid, walk.i, walk.j - 1, walk.seen, deadEnd)) {
                //                Set<String> seen = walk.seen;
                //                if (possibleDirections > 1) {
                //                    seen = new HashSet<>(walk.seen);
                //                }
                stack.add(new StackElement(walk.i, walk.j - 1, new HashSet<>(walk.seen)));
            }
            //            if (canGo(grid, walk.i, walk.j + 1, walk.seen, deadEnd) && (grid[walk.i][walk.j] == '.' || grid[walk.i][walk.j] == '>')) {
            if (canGo(grid, walk.i, walk.j + 1, walk.seen, deadEnd)) {
                //                Set<String> seen = walk.seen;
                //                if (possibleDirections > 1) {
                //                    seen = new HashSet<>(walk.seen);
                //                }
                stack.add(new StackElement(walk.i, walk.j + 1, new HashSet<>(walk.seen)));
            }
        }

        walkLengths.sort(Comparator.comparingInt(i -> i));
        System.out.println(walkLengths);
        return walkLengths.getLast();
    }

    private static int countDeadEnd(char[][] grid, int i, int j, Set<String> deadEnd) {
        int count = 0;
        if (deadEnd.contains(key(i - 1, j))) {
            count++;
        }
        if (deadEnd.contains(key(i + 1, j))) {
            count++;
        }
        if (deadEnd.contains(key(i, j - 1))) {
            count++;
        }
        if (deadEnd.contains(key(i, j + 1))) {
            count++;
        }

        return count;
    }

    private static int countPossibleDirections(char[][] grid, int i, int j, Set<String> seen) {
        int count = 0;
        if (canGo(grid, i - 1, j, seen, new HashSet<>())) {
            count++;
        }
        if (canGo(grid, i + 1, j, seen, new HashSet<>())) {
            count++;
        }
        if (canGo(grid, i, j - 1, seen, new HashSet<>())) {
            count++;
        }
        if (canGo(grid, i, j + 1, seen, new HashSet<>())) {
            count++;
        }
        return count;
    }

    private static boolean canGo(char[][] grid, int i, int j, Set<String> seen, Set<String> deadEnd) {
        if (i >= 0 && i < grid.length && j > -0 && j < grid[0].length && grid[i][j] != '#' && !seen.contains(key(i, j)) && !deadEnd.contains(key(i, j))) {
            return true;
        }
        return false;
    }

    private static void printGrid(char[][] grid, Set<String> seen) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (seen.contains(key(i, j))) {
                    System.out.print('O');
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void printGrid(char[][] grid) {
        for (char[] chars : grid) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
}

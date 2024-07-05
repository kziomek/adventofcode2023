package advent.day10.ver2;

//import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PipeMaze {

    public static void main(String[] args) throws IOException {
        //        char[][] grid = Parser.parse("src/main/resources/day10/example1.txt");
        //        char[][] grid = Parser.parse("src/main/resources/day10/example2.txt");
        //        char[][] grid = Parser.parse("src/main/resources/day10/example3.txt");
        char[][] grid = Parser.parse("src/main/resources/day10/my-input.txt");

        printGrid(grid);

        Node start = findStart(grid);
        int distance = bfs(start, grid);
        System.out.println(distance);
    }

    private static int bfs(Node start, char[][] grid) {
        Map<Node, Integer> dist = new HashMap<>();
        Map<Node, Node> parents = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(start);

        dist.put(start, 0);

        boolean[][] seen = new boolean[grid.length][grid[0].length];
        seen[start.row()][start.col()] = true;

        int maxDist = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            List<Node> unseenNeighbours = findUnseenNeighbours(node, grid, seen);

            for (Node unseenNeighbour : unseenNeighbours) {
                queue.add(unseenNeighbour);
                seen[unseenNeighbour.row()][unseenNeighbour.col()] = true;
                dist.put(unseenNeighbour, dist.get(node) + 1);
                parents.put(unseenNeighbour, node);
            }

            if (queue.isEmpty()) {
                maxDist = dist.get(node);
                printShortestPath(node, parents);
            }
        }

        printSeen(seen);

        return maxDist;
    }

    private static void printShortestPath(Node node, Map<Node, Node> parent) {
        Node currentNode = node;
        do {
            System.out.println(currentNode.row() + " " + currentNode.col());
            currentNode = parent.get(currentNode);
        } while (currentNode != null);
    }

    private static List<Node> findUnseenNeighbours(Node node, char[][] grid, boolean[][] seen) {

        List<Node> unseenNeighbours = new ArrayList<>();

        // north
        if (isWithinGrid(node.row() - 1, node.col(), grid)
            && !seen[node.row() - 1][node.col()]
            && "S|LJ".contains(Character.toString(grid[node.row()][node.col()]))
            && "S|F7".contains(Character.toString(grid[node.row() - 1][node.col()]))) {
            unseenNeighbours.add(new Node(node.row() - 1, node.col()));
        }

        // south
        if (isWithinGrid(node.row() + 1, node.col(), grid)
            && !seen[node.row() + 1][node.col()]
            && "S|F7".contains(Character.toString(grid[node.row()][node.col()]))
            && "S|LJ".contains(Character.toString(grid[node.row() + 1][node.col()]))) {
            unseenNeighbours.add(new Node(node.row() + 1, node.col()));
        }

        //east
        if (isWithinGrid(node.row(), node.col() + 1, grid)
            && !seen[node.row()][node.col() + 1]
            && "S-LF".contains(Character.toString(grid[node.row()][node.col()]))
            && "S-J7".contains(Character.toString(grid[node.row()][node.col() + 1]))) {
            unseenNeighbours.add(new Node(node.row(), node.col() + 1));
        }

        //west
        if (isWithinGrid(node.row(), node.col() - 1, grid)
            && !seen[node.row()][node.col() - 1]
            && "S-J7".contains(Character.toString(grid[node.row()][node.col()]))
            && "S-LF".contains(Character.toString(grid[node.row()][node.col() - 1]))) {
            unseenNeighbours.add(new Node(node.row(), node.col() - 1));
        }

        return unseenNeighbours;
    }

    private static boolean isWithinGrid(int r, int c, char[][] field) {
        return r >= 0 && c >= 0 & r < field.length && c < field[0].length;
    }

    private static Node findStart(char[][] field) {
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[0].length; c++) {
                if (field[r][c] == 'S') {
                    return new Node(r, c);
                }
            }
        }
        return null;
    }

    private static void printGrid(char[][] freshField) {
        for (char[] chars : freshField) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    private static void printSeen(boolean[][] freshField) {
        for (boolean[] values : freshField) {
            for (boolean value : values) {
                System.out.print(value ? 'O' : '.');
            }
            System.out.println();
        }
    }
}

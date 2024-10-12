package advent.day19;

import java.util.ArrayList;
import java.util.List;

public class Node {
    String key;
//    List<Edge> targets;
    List<Edge> prevs;

    public Node(String key) {
        this.key = key;
//        targets = new ArrayList<>();
        prevs = new ArrayList<>();
    }
    //    Workflow workflow;
}

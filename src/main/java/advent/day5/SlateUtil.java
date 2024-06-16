package advent.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SlateUtil {

    public static void main(String[] args) {
//        Slate startSlate = new Slate(0, Long.MAX_VALUE);

        List<Slate> list1 = new ArrayList<>();
        list1.add(new Slate(0, Long.MAX_VALUE));

//        Slate soil1 = new Slate(56, 59);
//        Slate soil2 = new Slate(60, 96);

        List<Slate> list2 = new ArrayList<>();
        list2.add(new Slate(60, 96));
        list2.add(new Slate(56, 59));



        split(list1, list2);

    }

    /*
        0            100
        56 59       60 96

        s1 start < s2 start || s2 start < s1 start -> wyrownaj start
        lower start - hihger start - 1
        0 - 55
        s1 start = s2 start

        56            100
        56 59       60 96

        s1 start == s2 start ->
          -> s2


     */
    public static List<Slate> split(List<Slate> l1, List<Slate> l2) {

        System.out.println("");

        Collections.sort(l1);
        Collections.sort(l2);

        System.out.println("");

        Queue<Slate> q1 = new LinkedList<>(l1);
        Queue<Slate> q2 = new LinkedList<>(l2);



        List<Slate> splits = new LinkedList<>();


        Slate s1 = q1.poll();
        Slate s2 = q2.poll();


        long lowerStart = Math.min(s1.start, s2.start);
        long higherStart = Math.max(s1.start, s2.start);


        if (lowerStart < higherStart) {
            splits.add(new Slate(lowerStart, higherStart - 1));
            s1 = new Slate(higherStart, s1.end);
            s2 = new Slate(higherStart, s2.end);
        } else if (lowerStart == higherStart) {
            long lowerEnd = Math.min(s1.end, s2.end);
            splits.add(new Slate(lowerStart, lowerEnd));
//            if (s1)


        }



        System.out.println();

        return null;
    }
}



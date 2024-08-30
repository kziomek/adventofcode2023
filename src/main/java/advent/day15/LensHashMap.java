package advent.day15;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

public class LensHashMap {

    LinkedList<Len>[] boxes;

    @SuppressWarnings("unchecked")
    public LensHashMap() {
        int size = 256;
        boxes = (LinkedList<Len>[]) new LinkedList<?>[size];
        for (int i = 0; i < size; i++) {
            boxes[i] = new LinkedList<>();
        }
    }

    public void put(Len len) {
        long boxIndex = calculateHash(len.getLabel());
        LinkedList<Len> box = boxes[(int) boxIndex];
        Optional<Len> firstLen = box.stream().filter(lenInBox -> lenInBox.getLabel().equals(len.getLabel())).findFirst();
        if (firstLen.isPresent()) {
            firstLen.get().replaceSize(len.getSize());
        } else {
            box.addLast(len);
        }
    }

    public void delete(String label) {
        long boxIndex = calculateHash(label);
        LinkedList<Len> box = boxes[(int) boxIndex];
        box.removeIf(lenInBox -> lenInBox.getLabel().equals(label));
    }

    private static long calculateHash(String s) {
        return s.chars()
            .reduce(0, (c, i) -> 17 * (c + i) % 256);
    }

    public long focusingPower() {
        int focusingPower = 0;

        for (int i = 0; i < 256; i++) {
            Iterator<Len> iter = boxes[i].iterator();
            int slot = 0;
            while (iter.hasNext()) {
                Len len = iter.next();
                slot++;
                focusingPower += (i + 1) * slot * len.getSize();
            }
        }
        return focusingPower;
    }
}

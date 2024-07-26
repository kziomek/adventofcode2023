package day12

import advent.day12.RowRecord
import spock.lang.Specification

class RowRecordSpec extends Specification{

    def "validate record"(){
        expect:
        new RowRecord("#.#.### 1,1,3").isValid()
        new RowRecord(".#...#....###. 1,1,3").isValid()
        new RowRecord(".#.###.#.###### 1,3,1,6").isValid()
        new RowRecord("####.#...#... 4,1,1").isValid()
        new RowRecord("#....######..#####. 1,6,5").isValid()
        new RowRecord(".###.##....# 3,2,1").isValid()
    }
}

package day18

import advent.day18.Line
import advent.day18.LineUtils
import advent.day18.SweepLineAlgorithm
import spock.lang.Specification

class LineUtilsSpec extends Specification {

    def "subtract lines"() {
        when:
        List<Line> difference = LineUtils.subtractLines(minuend, subtrahend)
        then:
        difference == expectedDifference
        where:
        minuend           | subtrahend        | expectedDifference
        new Line(0, 1, 2) | new Line(0, 3, 4) | [new Line(0, 1, 2)]
        new Line(0, 3, 4) | new Line(0, 1, 2) | [new Line(0, 3, 4)]
        new Line(0, 1, 3) | new Line(0, 3, 4) | [new Line(0, 1, 2)]
        new Line(0, 1, 4) | new Line(0, 3, 6) | [new Line(0, 1, 2)]

        new Line(0, 3, 6) | new Line(0, 1, 4) | [new Line(0, 5, 6)]
        new Line(0, 4, 6) | new Line(0, 1, 4) | [new Line(0, 5, 6)]

        new Line(0, 1, 2) | new Line(0, 1, 2) | []
        new Line(0, 2, 3) | new Line(0, 1, 4) | []
        new Line(0, 1, 6) | new Line(0, 2, 4) | [new Line(0, 1, 1), new Line(0, 5, 6)]
    }

    def "subtract multiple lines"() {
        when:
        List<Line> difference = LineUtils.subtractLines(minuends, subtrahends)

        then:
        difference == expectedDifference
        where:
        minuends                               | subtrahends                            | expectedDifference
        [new Line(0, 1, 5), new Line(0, 7, 9)] | [new Line(0, 2, 3), new Line(0, 8, 8)] | [new Line(0, 1, 1), new Line(0, 4, 5), new Line(0, 7, 7), new Line(0, 9, 9)]
        [new Line(0, 1, 9)]                    | [new Line(0, 2, 3), new Line(0, 8, 8)] | [new Line(0, 1, 1), new Line(0, 4, 7), new Line(0, 9, 9)]
        [new Line(0, 2, 5)]                    | [new Line(0, 1, 2), new Line(0, 4, 5)] | [new Line(0, 3, 3)]
    }

    def "join adjacent lines"() {
        when:
        def result = LineUtils.joinAdjacent(lines)

        then:
        result == expectedResult

        where:
        lines                                                                        | expectedResult
        [new Line(0, 1, 2), new Line(0, 2, 3)]                                       | [new Line(0, 1, 3)]
        [new Line(0, 1, 2), new Line(0, 2, 3), new Line(0, 5, 6), new Line(0, 6, 8)] | [new Line(0, 1, 3), new Line(0, 5, 8)]

    }

    def "merge lines"() {
        when:
        def result = LineUtils.merge(linesA, linesB)
        then:
        result == expectedResult
        where:
        linesA              | linesB              | expectedResult      | description
        [new Line(0, 1, 2)] | [new Line(0, 1, 2)] | []                  | "This is closing line"
        []                  | [new Line(0, 1, 2)] | [new Line(0, 1, 2)] | "This is closing opening line"


    }

}

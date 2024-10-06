package day18

import advent.day18.Line
import advent.day18.LineUtils
import advent.day18.SweepLineAlgorithm
import spock.lang.Specification

class SweepLineAlgorithmSpec extends Specification {

    def "merge lines"() {
        when:
        def result = SweepLineAlgorithm.mergeSweepLinesWithNextLines(linesA, linesB)
        then:
        result == expectedResult
        where:
        linesA              | linesB              | expectedResult      | description
        [new Line(0, 1, 2)] | [new Line(0, 1, 2)] | []                  | "closing line"
        []                  | [new Line(0, 1, 2)] | [new Line(0, 1, 2)] | "fully opening line"


    }
}

package day18

import advent.day18.Line
import advent.day18.Point
import advent.day18.SweepLineAlgorithm
import advent.day4.Card
import spock.lang.Specification

class SweepingAlgorithmSpec extends Specification {

    def "subtract lines"() {
        given:
        Line minuend = new Line(new Point(0, 1), new Point(0, 2))
        Line subtrahend = new Line(new Point(0, 3), new Point(0, 4))
        when:
        Line difference = SweepLineAlgorithm.subtractLines(minuend, subtrahend)
        then:
        difference == new Line(new Point(0, 1), new Point(0, 2))
    }


    def "subtract lists"() {
        given:
        List<Line> minuend = new ArrayList<>()
        List<Line> subtrahend = new ArrayList<>()

        when:
        def card = new SweepLineAlgorithm()

        then:
        card.cardValue() == expectedResult

        where:
        cardName | winningNumbers              | numbersYouHave                          | expectedResult
        "Card 1" | [41, 48, 83, 86, 17] as Set | [83, 86, 6, 31, 17, 9, 48, 53] as Set   | 8
        "Card 2" | [13, 32, 20, 16, 61] as Set | [61, 30, 68, 82, 17, 32, 24, 19] as Set | 2
        "Card 3" | [1, 21, 53, 59, 44] as Set  | [69, 82, 63, 72, 16, 21, 14, 1] as Set  | 2
        "Card 4" | [41, 92, 73, 84, 69] as Set | [59, 84, 76, 51, 58, 5, 54, 83] as Set  | 1
        "Card 5" | [87, 83, 26, 28, 32] as Set | [88, 30, 70, 12, 93, 22, 82, 36] as Set | 0
        "Card 6" | [31, 18, 13, 56, 72] as Set | [74, 77, 10, 23, 35, 67, 36, 11] as Set | 0
    }

}

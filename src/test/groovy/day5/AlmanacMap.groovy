package day5

import advent.day5.AlmanacMap
import spock.lang.Specification


class AlmanacMapSpec extends Specification {

    def 'test mapping'() {
        given:
        def map = new AlmanacMap(50, 98, 2)

        expect:
        map.match(98)
        map.match(99)
        map.mapDestination(98) == 50
        map.mapDestination(99) == 51

        !map.match(50)
        !map.match(97)
        !map.match(100)
    }
}